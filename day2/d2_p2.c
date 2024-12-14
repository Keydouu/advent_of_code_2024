#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#define MAX_LINE_LENGTH 25

int* readLine(const char* input, int* size){
    int count = 0;
    const char* temp = input;
    while (*temp) {
        if (*temp == ' ') {
            count++;
        }
        temp++;
    }
    count++;
    int* result = (int*)malloc(count * sizeof(int));
    if (result == NULL) {
        printf("Memory allocation failed");
        *size = 0;
        return NULL;
    }

    char* token;
    char* input_copy = strdup(input);
    *size = 0;

    token = strtok(input_copy, " ");
    while (token != NULL) {
        result[*size] = atoi(token); 
        (*size)++;
        token = strtok(NULL, " ");
    }

    free(input_copy);

    return result;
}

bool pairIncrease(int last_read, int new_read){
    return (new_read-last_read>=1) && (new_read-last_read<=3);
}
bool pairDecrease(int last_read, int new_read){
    return (last_read-new_read>=1) && (last_read-new_read<=3);
}

bool arrayOk(int* lineAsInt, int size, bool (*compare_pair)(int, int), bool forgive){
    int new_read;
    int i=1;
    int last_read=lineAsInt[0];

    while (i<size) {
        new_read = lineAsInt[i];
        if (!compare_pair(last_read, new_read)){
            bool output = false;
            if (forgive){
                if (i < (size-1)){//attempt to delete new_read
                    if (compare_pair(last_read, lineAsInt[i+1]) )
                        output = arrayOk(lineAsInt+i+1, size-i-1, compare_pair, false);
                } else if (i == size-1)
                    return true;
                if (!output){//attempt to delete last_read, by comparing with the one before
                    if (i==1)
                        return arrayOk(lineAsInt+i, size-i, compare_pair, false);
                    if (compare_pair(lineAsInt[i-2], new_read))
                        return arrayOk(lineAsInt+i, size-i, compare_pair, false);
                }
            }
            return output;
        }
        last_read=new_read;
        i++;
    }
    return true;
}

int main() {
    FILE *file = fopen("input.sql", "r");
    if (file == NULL) {
        perror("Unable to open file");
        return 1;
    }

    int safes = 0;
    
    char line[MAX_LINE_LENGTH];
    int* lineAsInt;

    int size;
    
    while (fgets(line, sizeof(line), file) != NULL) {
        lineAsInt = readLine(line, &size);
        if (arrayOk(lineAsInt, size, pairIncrease, true)){
            safes++;
        } else if (arrayOk(lineAsInt, size, pairDecrease, true)){
            safes++;
        }
    }

    fclose(file);

    printf("Safe lines : %d\n", safes);

    return 0;
}