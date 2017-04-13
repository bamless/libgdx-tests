#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

const float p = 0.005;
const vec2 center = vec2(0.5, 0.5);

void main() {
	vec2 diff = center - v_texCoords;
	float d = length(diff);
	vec2 dir = normalize(diff);
	
	vec2 uv = v_texCoords + dir * (p / (d*d + 0.01));
	
	vec3 col = texture2D(u_texture, uv).xyz;
	gl_FragColor = vec4(col.xyz, 1.0);
}