#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
//The center (in screen coordinates) of the blur
uniform vec2 cent;

//these things should be uniforms but nonmeva'
const float blurWidth = -0.85;
//the number of samples
#define NUM_SAMPLES 100

void main() {
	//compute vector from pixel to blur center 
	vec2 tc = v_texCoords - cent;
	//output color
	vec3 color = vec3(0.0);
	
	//sample the texture NUM_SAMPLES times
	for(int i = 0; i < NUM_SAMPLES; i++) {
		//sample the texture on the pixel-to-center line getting closer to the center every iteration
    	float scale = 1.0 + blurWidth * (float(i) / float(NUM_SAMPLES - 1));
    	color += texture2D(u_texture, (tc * scale) + cent).xyz;
  	}
	//normalize the pixel final color
	gl_FragColor = vec4(color / float(NUM_SAMPLES), 1.0);
}
