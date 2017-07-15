#version 400

uniform mat3 proj;
uniform mat3 model;
uniform mat3 texCoordProj;

in vec2 position;

out vec4 colorF;
out vec2 texF;

void main() {
    vec4 pos = vec4(vec3(position, 1.) * model * proj, 1.0);
    colorF = vec4(1., 1., 1., 1.);
    texF = (vec3(position, 1.) * texCoordProj).xy;
    gl_Position = pos;
}
