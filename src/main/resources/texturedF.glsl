#version 400

uniform sampler2D texture;

in vec4 colorF;
in vec2 texF;

out vec4 colorR;

void main() {
    colorR = texture2D(texture, texF);
}
