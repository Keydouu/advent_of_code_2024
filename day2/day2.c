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

int main() {
    FILE *file = fopen("input.sql", "r");
    if (file == NULL) {
        perror("Unable to open file");
        return 1;
    }

    int safes = 0;
    
    char line[MAX_LINE_LENGTH];
    int* lineAsInt;

    int current_sign = 0;// 1 increasing or -1 decreasing
    int last_read=0, new_read=0, size, i;
    bool lineOk;
    
    while (fgets(line, sizeof(line), file) != NULL) {
        current_sign = 0;// 1 increasing or -1 decreasing
        lineOk = true;


        lineAsInt = readLine(line, &size);
        i=1;
        last_read=lineAsInt[0];
        while (i<size) {
            new_read = lineAsInt[i];
            if (current_sign == 1){//increasing
                if ( !(new_read-last_read>=1) || !(new_read-last_read<=3)){
                    lineOk = false;
                    break;
                }
            } else if (current_sign == -1){//decreasing
                if ( !(last_read-new_read>=1) || !(last_read-new_read<=3)){
                    lineOk = false;
                    break;
                }
            } else if (current_sign == 0){// first pair
                if ( (new_read-last_read>=1)&&(new_read-last_read<=3))
                    current_sign = 1;
                else if ( (last_read-new_read>=1) && (last_read-new_read<=3))
                    current_sign = -1;
                else {
                    lineOk = false;
                    break;
                }
            }
            last_read=new_read;
            i++;
        }
        if (lineOk)
            safes++;
    }

    fclose(file);

    printf("Safe lines : %d\n", safes);

    return 0;
}