/*
 *==============================================================================
 * Name        : hw2_perm152.c
 * Author      : Pawan Chandra
 * 4 Digit     : 5020
 * Date        : September 20, 2019
 * Description : The code below takes an unsigned char array of size 64 and performs
 * 	       : bit toggling. The steps the code uses to toggle follows the AXR
 * 	       : layout.
 *==============================================================================
 */

#include <stdio.h>
#include <inttypes.h>
#include <string.h>

//Method declaration 
static void update();
static uint32_t rotl();


/*
*  The values passed to perm152 are a char array of size 64 and the parameter also contains 
*  an array that the output will be copied to using memcpy.
*/
void perm152(unsigned char *in,unsigned char *out){

	//create a unsigned int array size 16
	uint32_t a[16];

	//copy the contents of src array (in) to dst array (a). 
	//Note: size could be hard coded since we are given array size
	memcpy(a, in, sizeof(a));

	//The output of the array will display the new array a.
	//Note: The printf does NOT print leading 0's. Ex 012 = 12. Also the hex is printed backwords
	//for(int i=0;i<16;i++){printf("%x\n",a[i]);}
	
	
	//For-Loop runs 10 iteration of the below update command.
	for(int i=0; i<10; i++){
		update(&a[0], &a[4], &a[8],  &a[12]);
		update(&a[1], &a[5], &a[9],  &a[13]);
		update(&a[2], &a[6], &a[10], &a[14]);
		update(&a[3], &a[7], &a[11], &a[15]);
		update(&a[0], &a[5], &a[10], &a[15]);
		update(&a[1], &a[6], &a[11], &a[12]);
		update(&a[2], &a[7], &a[8],  &a[13]);
		update(&a[3], &a[4], &a[9],  &a[14]);
	}
	
	//copy the contents of src(a) to the output dst(out) 
	memcpy(out, a, 64);
}

/*
* update performs bit manipulation following the AXR steps taught in class
* Note: Passed pointer in the parameter since there is no way to return the 
*		values back to perm152
*/
static void update(unsigned int* w, unsigned int* x, unsigned int* y, unsigned int* z){
    *w = *w + *x; *z = (*z ^ *w); *z = rotl(*z, 16); //Add ... XOR ... Rotate 
    *y = *y + *z; *x = (*x ^ *y); *x = rotl(*x, 12); //...
    *w = *w + *x; *z = (*z ^ *w); *z = rotl(*z, 8);
    *y = *y + *z; *x = (*x ^ *y); *x = rotl(*x, 7);
}

/*
* The command below rotates or shifts the bits by x number of ammount
* Note: Rotates left. int shift is provided by method update
*/
static uint32_t rotl(uint32_t temp, int shift){
    return temp = (temp << shift) | (temp >> (64-shift));
}


//Test Case: The test case was taken from piazza from a student name Chris Stevens
//Reminder: Verify the printf works correctly using %02x 

/*int main(){
	unsigned char in[64] = {
			0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1,
			0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1,
			0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1,
			0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1, 0x1,0x1,0x1,0x1
	};
	unsigned char out[64];
	
	perm152(in,out);
	int count = 0;
	for(int i=0; i<64; i++){
		printf("%02x",out[i]);
		count++;
		if(count % 4 == 0){
			printf("\n");
		}
	}
}*/
