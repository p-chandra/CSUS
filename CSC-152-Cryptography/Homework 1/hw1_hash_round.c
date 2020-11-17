/*
 *============================================================================
 * Name        : hw1_hash_round.c
 * Author      : Pawan Chandra
 * 4 Digit     : 5020
 * Date        : September 8, 2019
 * Description : This program takes an array then convert little endian to big.
 *             : Using the map provided by the professor, do some data manipulation
 *             : and finally convert it back to little endian
 *============================================================================
 */

#include <stdint.h>
#include <inttypes.h>
#include <stdio.h>
#include <string.h>

void rotate();

void hash_round(uint64_t *v)
{
    //Rotate the hex values from little endian to big endian
    rotate(v);

    //Code below manipulates data but adding, shifting, and xoring
    v[0] = v[0] + v[1];
    v[2] = v[3] + v[2];
    v[1] = (v[1] << 13) | (v[1] >> (64-13));
    v[3] = (v[3] << 16) | (v[3] >> (64-16));

    v[1] = v[1] ^ v[0];
    v[0] = (v[0] << 32) | (v[0] >> (64-32));
    v[3] = v[3] ^ v[2];

    v[2] = v[2] + v[1];
    v[1] = (v[1] << 17) | (v[1] >> (64-17));
    v[1] = v[1] ^ v[2];
    v[2] = (v[2] << 32) | (v[2] >> (64-32));

    v[0] = v[0] + v[3];
    v[3] = (v[3] << 21) | (v[3] >> (64-21));
    v[3] = v[3] ^ v[0];

    //rotate the data back from big to little endian
    rotate(v);
}

void rotate(uint64_t *v){
	//using a for-loop, I shift each element in the array
	for(int x=0; x<4; x++)
	    {
		//take pieces of the hex starting from the end
	    	uint64_t a = (v[x] & 0x00000000000000FF) << 56;
	        uint64_t b = (v[x] & 0x000000000000FF00) << 40;
	        uint64_t c = (v[x] & 0x0000000000FF0000) << 24;
	        uint64_t d = (v[x] & 0x00000000FF000000) << 8;
	        uint64_t e = (v[x] & 0x000000FF00000000) >> 8;
	        uint64_t f = (v[x] & 0x0000FF0000000000) >> 24;
	        uint64_t g = (v[x] & 0x00FF000000000000) >> 40;
	        uint64_t h = (v[x] & 0xFF00000000000000) >> 56;
	        //Assemble all the parts together
	        v[x] = a | b | c | d | e | f | g | h;
	    }
}
