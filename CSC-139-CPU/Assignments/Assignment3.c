#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include "buffer.h"

void *producer(void *param);
void *consumer(void *param);

pthread_mutex_t mutex;
sem_t empty, full;
buffer_item buffer[BUFFER_SIZE];
int count = 0, itemInsert = 0, itemRemove = 0;

int insert_item(buffer_item item) {
   int temp = 0;
   sem_wait(&empty);
   pthread_mutex_lock(&mutex);
   if(count != BUFFER_SIZE) {
      buffer[itemInsert] = item;
      itemInsert = (itemInsert+1)%BUFFER_SIZE;
      count++;
      temp = 0;
   }else{
      temp = temp - 1;
   }
   pthread_mutex_unlock(&mutex);
   sem_post(&full);
   return temp;
}

int remove_item(buffer_item *item) {
   int temp = 0;
   sem_wait(&full);
   pthread_mutex_lock(&mutex);
   if(count != 0) {
      *item = buffer[itemRemove];
      itemRemove = (itemRemove+1)%BUFFER_SIZE;
      count--;
      temp = 0;
   }else{
      temp = temp - 1;
   }
   pthread_mutex_unlock(&mutex);
   sem_post(&empty);
   return temp;
}

void *producer(void *param) {
   buffer_item item;
   while(1) {
      sleep(rand() % 9 + 1);
      item = rand();
      if(insert_item(item)) {
         printf("Producer: Error\n");
      }else
         printf("Produced: %d\n", item);
      fflush(stdout);
         }
}

void *consumer(void *param) {
   buffer_item item;
   while(1) {
      sleep(rand() % 9 + 1);
      if(remove_item(&item))
         printf("Consumer: Error\n");
      else
         printf("Consumed: %d\n", item);
      fflush(stdout);
   }
}


int main(int argc, char *argv[]) {
   if(argc != 4)
      printf("Please make sure to use 'a.out int int int' format\n");

   int sleepTime = atoi(argv[1]);
   int numProducer = atoi(argv[2]); 
   int numConsumer = atoi(argv[3]); 

   int i;
   srand(time(NULL));
   pthread_mutex_init(&mutex, NULL);
   sem_init(&empty, 0, 1);
   sem_init(&full, 0, 0);
   count = itemInsert = itemRemove = 0;
   pthread_t prod[numProducer];
   pthread_t cons[numConsumer];
   for(i = 0; i < numProducer; i++)
      pthread_create(&prod[i], NULL, producer, &i);
   for(i = 0; i < numConsumer; i++)
      pthread_create(&cons[i], NULL, consumer, &i);
   sleep(sleepTime);
   printf("Exit\n");
   return 0;
   }