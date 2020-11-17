#!/bin/bash
clang -g -O0 -std=c99 -Werror -Wall -Wextra -Wconversion -fsanitize=address hw2_perm152.c hw4_perm152hash.c -o output
./output
