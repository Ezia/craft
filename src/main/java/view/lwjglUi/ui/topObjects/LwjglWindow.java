package view.lwjglUi.ui.topObjects;

import view.lwjglUi.shaderProgram.*;
import view.lwjglUi.ui.LwjglElement;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import view.ui.topContainers.UIWindow;
import util.math.Matrix;
import util.shape.Rectangle;
import util.math.Vector;

import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.TreeMap;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LwjglWindow extends UIWindow<LwjglElement, LwjglLayer> {
	private long window = 0;

	private LinkedList<LwjglProgram> programs = new LinkedList<>();

	private TreeMap<LwjglProgramPreset, LwjglProgram> prog = new TreeMap<>();

	private Matrix projectionMatrix;

	public LwjglWindow(Rectangle box, String name) {
		super(box, name);
	}

	public void draw() throws LwjglProgramException {

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
//		glDisable(GL_CULL_FACE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		projectionMatrix = Matrix.projection2D(new Rectangle(new Vector(0., 0.), getBox().diag));

		for (LwjglProgram p : prog.values()) {
			updateProgramData(p);
		}

		for (LwjglLayer layer : layers) {
			layer.draw(this);
		}
	}

	public void start() throws LwjglProgramException {
		init();

		loop();

		end();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
//		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
//		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		// Create the window
		window = glfwCreateWindow((int)this.getBox().diag.x(),
				(int)this.getBox().diag.y(),
				getName(), NULL, NULL);
		if ( window == 0 )
			throw new RuntimeException("Failed to create the GLFW window");

		initCallbacks();

		glfwSetWindowPos(
				window,
				(int)this.getBox().pos.x(),
				(int)this.getBox().pos.y()
		);

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
	}

	private void loop() throws LwjglProgramException {

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glfwSwapBuffers(window); // swap the color buffers

			syncBox();

			glViewport(0, 0,
					(int)getBox().diag.x(), (int)getBox().diag.y());

			draw();

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

	private void initCallbacks() {
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
	}

	/**
	 * Synchronize the window size from GLFW with the window Box
	 */
	private void syncBox() {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, w, h);
		IntBuffer x = BufferUtils.createIntBuffer(1);
		IntBuffer y = BufferUtils.createIntBuffer(1);
		glfwGetWindowPos(window, x, y);

		setBox(new Rectangle(
				new Vector((double)x.get(0), (double)y.get(0)),
				new Vector((double)w.get(0), (double)h.get(0))
		));
	}

	public LwjglProgram getProgram(LwjglProgramPreset preset) {
		if (!prog.containsKey(preset)) {
			try {
				LwjglProgram program = new LwjglProgram(preset);
				program.load();
				program.compile();
				program.link();
				updateProgramData(program);
				prog.put(preset, program);
			} catch (Exception e) {
				for (LwjglProgram progr : prog.values()) {
					progr.delete();
				}
				e.printStackTrace();
				System.exit(1);
			}
		}
		return prog.get(preset);
	}

	private void updateProgramData(LwjglProgram program) throws LwjglProgramException {
		switch (program.preset) {
			case CRAFT_COLORED_VERTEX2D:
				program.use();
				int proj = glGetUniformLocation(program.get(), "proj");
				glUniformMatrix3fv(proj, false, projectionMatrix.getFloatColumnArray());
				program.unuse();
				break;
			default:
				throw new LwjglProgramException("Unknown program.");
		}
	}

	private void end() {
		for (LwjglProgram prog : programs) {
			prog.delete();
		}
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}
