#version 400

uniform mat3 proj;
uniform mat3 model;
//uniform vec4 color;

in vec2 position;
in vec2 tex;

out vec4 colorF;
out vec2 texF;

void main() {
    vec4 pos = vec4(vec3(position, 1.) * model * proj, 1.0);
//    gl_Position = vec4(position, 0., 1.0);
//    colorF = color;
    colorF = vec4(1., 1., 1., 1.);
    texF = tex;
    gl_Position = pos;
}
