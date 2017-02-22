#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform sampler2D u_displacement;
uniform float timedelta;

void main() {
	vec2 displacement = texture2D(u_displacement, v_texCoords/8.0).xy;
	float t = v_texCoords.y + displacement.y * 0.1 - 0.07 + (sin(v_texCoords.x * 40.0 + timedelta*6.0) * 0.005);
	gl_FragColor = v_color * texture2D(u_texture, vec2(v_texCoords.x,t));
}