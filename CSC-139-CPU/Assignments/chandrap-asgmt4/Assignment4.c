#include<stdio.h>
#include<string.h>
#include<stdlib.h>	
#include<time.h>
#include<stdbool.h>

int i;
int fr[10];
bool flag = true;

void FIFO(int psize, int n, int *pg){
	int num = n;
	int count = 0, f = 0, s = 0, r = 0;
	int i;
	for (i = 0; i<psize; i++)
		fr[i] = 0;
	while (s<n){
			flag = true;
			num = pg[s];
			for (i = 0; i<psize; i++){
				if (num == fr[i]){
					s++;	
					flag = false;
					printf("Page %d already in Frame %d\n", fr[i], i);
					break;
				}
			}
			if (flag == true){
				if (r<psize){
					printf("Page %d loaded into Frame %d\n", pg[s], r);
					fr[r] = pg[s];
					r++;	
					s++;
					count++;
				}else{
					if (f<psize){
						printf("Page %d unloaded from Frame %d, ",fr[f], f);
						printf("Page %d loaded into Frame %d\n", pg[s], f);
						fr[f] = pg[s];
						s++;
						f++;
						count++;
					}else
						f = 0;
				}
			}
	}
	printf("%d Page faults\n\n", count);
}

void LRU(int psize, int n, int *pg){
	int count = 0, f = 0, s = 0, r = 0, k = 0;
	int fault = 0;
	int count2[10];
	int temp, least, p;

	f = psize;
		int i;
		for (i = 0; i<f; i++){
			count2[i] = 0;
			fr[i] = -1;
		}
		for (i = 0; i<n; i++){
			flag = true;
			temp = pg[i];
			int j;
			for (j = 0; j<f; j++){
				if (temp == fr[j]){
					flag = false;
					count2[j] = i;
					printf("Page %d already in Frame %d\n", fr[j], j);
					break;
				}
			}
			if ((flag == true) && (k<f)){
				fault++;
				fr[k] = temp;
				count2[k] = i;
				printf("Page %d loaded into Frame %d\n", fr[k], k);
				k++;
			}
			else if ((flag == true) && (k == f)){
				fault++;
				least = count2[0];
				int m;
				for (m = 0; m<f; m++){
					if (count2[m]<least){
						least = count2[m];
						p = m;
					}
				}
				fr[p] = temp;
				count2[p] = i;
				printf("Page %d unloaded from Frame %d, ",pg[least], p);
				printf("Page %d loaded into Frame %d\n", fr[p], p);
				p = 0;
			}
		}
		printf("%d Page faults\n\n", fault);
}

void OPT(int psize, int n, int *pg){
	int fSize;
	int count2[10];
	int temp, max, p = 0, k = 0;
	int tempflag = 0, fault = 0;

	fSize = psize; 
		int i;
		for (i = 0; i<fSize; i++){
			count2[i] = 0;
			fr[i] = -1;
		}
		for (i = 0; i<n; i++){
			flag = true;
			temp = pg[i];
			int j;
			for (j = 0; j<fSize; j++){
				if (temp == fr[j]){
					flag = false;
					printf("Page %d already in Frame %d\n", fr[j], j);
					break;
				}
			}
			if ((flag == true) && (k<fSize)){
				fault++;
				fr[k] = temp;
				printf("Page %d loaded into Frame %d\n", fr[k], k);
				k++;
			}else if ((flag == true) && (k == fSize)){
				fault++;
				int l;
				for (l = 0; l<fSize; l++)
					count2[l] = 0;
				int m;
				for (m = 0; m<fSize; m++){
					tempflag = 0;
					int r;
					for (r = i + 1; r<n; r++){
						if (fr[m] == pg[r]){
							if (count2[m] == 0)
								count2[m] = r;							
							tempflag = 1;
						}
					}
				if (tempflag != 1)
					count2[m] = n + 1;
			}
			max = count2[0];
			for (l = 0; l<fSize; l++){
				if (count2[l]>max){
					max = count2[l];
					printf("Page %d unloaded from Frame %d, ",fr[l], l);
					p = l;
				}
			}
			fr[p] = temp;
			printf("Page %d loaded into Frame %d\n", fr[p], p);
		}
	}
	printf("%d Page faults\n\n", fault);
}


int main(int argc, char  *argv[])
{
	int numPages, req, pg[30]; 
	int i = 0, count = 0;
	int frame, num, option;

	FILE *input;
	input = fopen(argv[1], "r");
	fscanf(input, "%d %d %d", &req, &frame, &numPages);
	while(!feof(input))
	{
		fscanf(input, "%d", &pg[i]);
		i++;
		count++;
	}		
	fclose(input);
	
	if(strcmp(argv[2], "FIFO")== 0){
		
		FIFO(frame,numPages,pg);
		option = 1;
		return 0;
	}else if(strcmp(argv[2], "LRU") == 0){
		
		LRU(frame,numPages,pg);
		option = 2;
		return 0;
	}else if(strcmp(argv[2], "OPT") == 0){
	
		OPT(frame,numPages,pg);		
		option = 3;
		return 0;
	}else{
		printf("Invalid Argument \n");
		return 1;
	}
}
