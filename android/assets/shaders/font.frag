/* An implementation of a distance field font shader */
#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float scale;

const float width = 0.5;

void main() {
	float smoothing = 1.0 / (4.0 * scale);
	float distance = texture2D(u_texture, v_texCoords).a;
	float alpha = smoothstep(width - smoothing, width + smoothing, distance) * v_color.a;
	gl_FragColor = vec4(v_color.rgb, alpha);
}