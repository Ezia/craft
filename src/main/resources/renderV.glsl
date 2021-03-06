#version 400

uniform mat3 mvp;

in vec2 position;
in vec4 color;

out vec4 colorF;

void main() {
    gl_Position = vec4(vec3(position, 1.) * mvp, 1.0);
//    gl_Position = vec4(position, 0., 1.0);
    colorF = color;
//    colorF = vec4(1., 1., 1., 1.);
}
