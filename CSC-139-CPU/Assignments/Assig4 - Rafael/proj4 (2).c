#include<stdio.h>
#include<string.h>
#include<stdlib.h>	
#include<time.h>

int main(int argc, char  *argv[])
{
	
	
	
	//===========================================================================
	int n,req, pg[30], fr[10];
	int cnt =0, h =0;
	int i, r,y, s, f,  count, flag, num, psize;
	int     j=0;
	f = 0;
	
	r = 0;
	
	s = 0;
	
	flag = 0;
	
	count = 0;
	
	
	int count2[10], fSize, fault,temp, k, current, c, dist, least, m, p, x,max, tempflag = 0;
	
	fault = 0;
	
	dist = 0;
	
	k = 0;
	
	int l = 0;
	
	
	
	
	//===========================================================================
	
	
	FILE *input;
	
	
	if(argv[2] == NULL ){
		
		printf("Enter a valid replacement algorith [FIFO|LRU|OPT] \n\n");
		exit(-1);
		
	}
	
	
	else if(argc >= 2){
		
		
		//+++++++++++++++++++FILE INPUT STARTS HERE++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		input = fopen(argv[1], "r");
		i=0;
		
		
		fscanf(input, "%d %d %d", &req, &psize, &n);
		
		while(!feof(input))
		{
			
			fscanf(input, "%d", &pg[i]);
			//printf("page = %d\n", pg[i]);
			i++;
			cnt++;
		}
		
	}else if(argv[1] == NULL){
		printf("Enter file name and try again...\n");
		exit(-1);
	}
	//cnt--;
	
	fclose(input);
	
	
	
	
	
	
	printf("\nTotal %d page requests were read from file\n",n);
	printf("Frame quantity: %d\n", psize);
	
	printf("Replacement Algorithm: %s\n\n", argv[2]);
	
	int selector = 0;
	
	if(strcmp(argv[2], "FIFO")== 0)
	{
		selector = 1;
	}
	else if(strcmp(argv[2], "LRU") == 0)
	{
		selector = 2;
	}
	else if(strcmp(argv[2], "OPT") == 0)
	{
		selector = 3;
	}
	
	else
	{
		printf("Enter a valid replacement algorith [FIFO|LRU|OPT] \n");
		exit(-1);
	}
	
	// printf("Selector =%d\n", selector);
	//psize = ;
	num = n;
	cnt = 0;
	switch(selector)
	{      
		//++++++++++++++++++++++++++++++FIFO STARTS HERE+++++++++++++++++++++++++++++++++++
		
	case 1:
		
		for (i = 0; i<psize; i++)
			
		{
			
			fr[i] = -1;
			
		}
		
		while (s<n)
			
		{
			
			flag = 0;
			
			num = pg[s];
			
			//check wether the page is already exist
			
			for (i = 0; i<psize; i++)
				
			{
				
				if (num == fr[i])
					
				{
					
					s++;
					
					flag = 1;
					printf("Page %d already in frame %d\n", fr[i], i);
					break;
					
				}
				
			}
			
			//if page is not already exist
			
			if (flag == 0)
				
			{
				
				if (r<psize)
					
				{
					
					fr[r] = pg[s];
					printf("Page %d loaded in frame %d\n", pg[s], r);
					r++;
					
					s++;
					
					count++;
					
				}
				
				else
					
				{
					
					if (f<psize)
						
					{
						printf("Page %d unloaded from frame %d, ",fr[f], f);
						fr[f] = pg[s];
						
						printf("Page %d loaded in frame %d\n", pg[s], f);
						s++;
						
						f++;
						
						count++;
						
					}
					
					else
						
						f = 0;
					//printf("this is a test\n  ");
				}
				
			}
			
			//print the page frame
			
			//printf("\n");
			
			//for (i = 0; i<psize; i++)
				
			//{
				
			//	printf("%d\t", fr[i]);
				
			//}
			
		}
		
		printf("\n%d Page Faults\n\n", count);
		printf("==============================================================\n");
		//getchar();
		
		
		
		
		
		break;
		//+++++++++++++++++++++++++++++++++++++++++++FIFO ENDS HERE++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
		//++++++++++++++++++++++++++++++++++++++++++LRU STARTS HERE++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
	case 2:
		
		
		f = psize;
		
		for (i = 0; i<f; i++)
			
		{
			
			count2[i] = 0;//helps to know recently used page
			
			fr[i] = -1;
			
		}
		
		for (i = 0; i<n; i++)
			
		{
			
			flag = 0;
			
			temp = pg[i];
			
			//check wether the page is already exist or not
			
			for (j = 0; j<f; j++)
				
			{
				
				if (temp == fr[j])
					
				{
					
					flag = 1;
					
					count2[j] = i;
					printf("Page %d already in frame %d\n", fr[j], j);
					break;
					
				}
				
			}
			
			//if the page is not already exist and frame has empty slot
			
			if ((flag == 0) && (k<f))
				
			{
				
				fault++;
				
				fr[k] = temp;
				
				count2[k] = i;
				printf("Page %d loaded in frame %d\n", fr[k], k);
				k++;
				
			}
			
			//if the page is not already exist and frame is full
			
			else if ((flag == 0) && (k == f))
				
			{
				
				fault++;
				
				//find the least recenlty used page
				
				least = count2[0];
				
				for (m = 0; m<f; m++)
					
				{
					
					if (count2[m]<least)
						
					{
						
						least = count2[m];
						
						p = m;
						
					}
					
				}
				
				fr[p] = temp;
				
				count2[p] = i;
				printf("Page %d unloaded from frame %d, ",pg[least], p);
				printf("Page %d loaded in frame %d\n", fr[p], p);
				p = 0;
				
			}
			
			//print the page frame
			
			//printf("\n");
			
			//for (x = 0; x<f; x++)
			//	
			//{
				
			//	printf("%d\t", fr[x]);
				
			//}
			
		}
		
		printf("\n%d Page Faults\n\n", fault);
		printf("==============================================================\n");
		
		break;
		//+++++++++++++++++++++++++++++++LRU ENDS HERE+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
	case 3:
		//++++++++++++++++++++++++++++++++OPT STARTS HERE++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		fSize = psize; 
		
		for (i = 0; i<fSize; i++)
			
		{
			
			count2[i] = 0;
			
			fr[i] = -1;
			
		}
		
		for (i = 0; i<n; i++)
			
		{
			
			flag = 0;
			
			temp = pg[i];
			
			//check wether the page is already exist
			
			for (j = 0; j<fSize; j++)
				
			{
				
				if (temp == fr[j])
					
				{
					
					flag = 1;
					printf("Page %d already in frame %d\n", fr[j], j);
					
					break;
					
				}
				
			}
			
			//if the page is not already exist and frame has empty slot
			
			if ((flag == 0) && (k<fSize))
				
			{
				
				fault++;
				
				fr[k] = temp;
				printf("Page %d loaded in frame %d\n", fr[k], k);
				
				k++;
				
				
				
			}
			
			//if the page is not already exist and frame is full
			
			else if ((flag == 0) && (k == fSize))
				
			{
				
				fault++;
				
				for (l = 0; l<fSize; l++)
					
				{
					
					count2[l] = 0;
					
				}
				
				//apply optimal replacemnt strategy
				
				for (m = 0; m<fSize; m++)
					
				{
					
					tempflag = 0;
					
					for (r = i + 1; r<n; r++)
						
					{
						
						if (fr[m] == pg[r])
							 
						{
							
							if (count2[m] == 0)
								
								count2[m] = r;
							
							tempflag = 1;
							
						}
						
					}
					
					if (tempflag != 1)
						
					{
						
						count2[m] = n + 1;
						
					}
					
				}
				
				
				p = 0;
				
				max = count2[0];
				
				for (l = 0; l<fSize; l++)
					
				{
					
					if (count2[l]>max)
						
					{
						
						max = count2[l];
						//printf("Page %d unloaded from frame %d, ",pg[]
						printf("Page %d unloaded from frame %d, ",fr[l], l);
						p = l;
						
					}
					
				}
				//printf("max: %d, p: %d, l: %d, \n", max, p, l);
				//printf("gp[%d] = %d \n", l,pg[m]);
				fr[p] = temp;
				
				printf("Page %d loaded in frame %d\n", fr[p], p);
			}
			
			
			
			
			
		}
		
		
		
		printf("\n%d Page Faults\n\n", fault);
		printf("==============================================================\n");
				
		break;
		
		
		//++++++++++++++++++++++++++++++++OPTENDS HERE++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
	default:
		printf("ERROR!! Enter a valid Algorithm\n");
		break;
		
	}   
	return -1;
}
