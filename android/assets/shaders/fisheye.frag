#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

//"in" attributes from our vertex shader
varying LOWP vec4 v_color;
varying vec2 v_texCoords;

//declare uniforms
uniform sampler2D u_texture;

//i'm too lazy to declare uniforms
const vec2 resolution = vec2(720, 1280);
const vec2 center = vec2(0.5, 0.5);

void main() {
	//normalized coords with some cheat 
	vec2 p = gl_FragCoord.xy / resolution.x;
	
	//screen proportion                                         
	float prop = resolution.x / resolution.y;
	//center coords
	vec2 m = vec2(0.5, 0.5 / prop);
	//vector from center to current fragment
	vec2 d = p - m;
	// distance of pixel from center
	float r = sqrt(dot(d, d)); 
	//amount of effect
	//float power = ( 2.0 * 3.141592 / (2.0 * sqrt(dot(m, m))) ) *(center.x / resolution.x - 0.5); NOOOO
			
	float power = 0.6789;
			
	//radius of 1:1 effect
	float bind;
	if (power > 0.0) bind = sqrt(dot(m, m));//stick to corners
	else {if (prop < 1.0) bind = m.x; else bind = m.y;}//stick to borders
	
	//Weird formulas
	vec2 uv;
	if (power > 0.0)//fisheye
	  	uv = m + normalize(d) * tan(r * power) * bind / tan( bind * power);
	else if (power < 0.0)//antifisheye
		uv = m + normalize(d) * atan(r * -power * 10.0) * bind / atan(-power * bind * 10.0);
	else 
	 	uv = p;//no effect for power = 1.0

	//Second part of cheat
	//for round effect, not elliptical
	gl_FragColor = texture2D(u_texture, vec2(uv.x, uv.y * prop));
}
