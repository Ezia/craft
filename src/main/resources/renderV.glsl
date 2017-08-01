#version 400

uniform mat3 proj;
uniform mat3 model;
uniform vec4 color;

in vec2 position;

out vec4 colorF;

void main() {
    vec4 pos = vec4(vec3(position, 1.) * model * proj, 1.0);
    gl_Position = pos;
    colorF = color;
}
