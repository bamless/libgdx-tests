#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
//The center (in screen coordinates) of the light source
uniform vec2 cent;

//The width of the blur (the smaller it is the further each pixel is going to sample)
const float blurWidth = -0.85;
//the number of samples
#define NUM_SAMPLES 100

void main() {
	//compute ray from pixel to light center
	vec2 tc = v_texCoords - cent;
	//output color
	vec3 color = vec3(0.0);

	//sample the texture NUM_SAMPLES times
	for(int i = 0; i < NUM_SAMPLES; i++) {
        //sample the texture on the pixel-to-center ray getting closer to the center every iteration
    	float scale = 1.0 + blurWidth * (float(i) / float(NUM_SAMPLES - 1));
        //summing all the samples togheter
    	color += (texture2D(u_texture, (tc * scale) + cent).xyz) / float(NUM_SAMPLES);
  	}
	//return final color
	gl_FragColor = vec4(color, 1.0);
}
