#include <stdio.h>
#include <stdlib.h>

int compare(const void *a, const void *b) {
    return (*(int *)a - *(int *)b);
}

int count_occurrences(int *arr, int size, int num) {
    int count = 0;
    for (int i = 0; i < size; i++) {
        if (arr[i] == num) {
            count++;
        } else if (arr[i] > num) {
            break; 
        }
    }
    return count;
}

int main() {
    FILE *file = fopen("input.sql", "r");
    if (file == NULL) {
        perror("Unable to open file");
        return 1;
    }

    // Determine input size
    int lines = 0;
    char ch;
    while ((ch = fgetc(file)) != EOF) {
        if (ch == '\n') {
            lines++;
        }
    }
    rewind(file);

    int *left = (int *)malloc(lines * sizeof(int));
    int *right = (int *)malloc(lines * sizeof(int));
    
    if (left == NULL || right == NULL) {
        perror("Memory allocation failed");
        fclose(file);
        return 1;
    }

    int n = 0;
    while (fscanf(file, "%d %d", &left[n], &right[n]) == 2) {
        n++;
    }

    fclose(file);

    qsort(left, n, sizeof(int), compare);
    qsort(right, n, sizeof(int), compare);

    int similarity_score = 0, last_number = -1, last_similarity = 0;
    for (int i = 0; i < n; i++) {
        if (left[i]!=last_number){
            last_similarity = count_occurrences(right, n, left[i]) * left[i];
            last_number = left[i];
        }
        similarity_score+=last_similarity;
    }

    printf("Similarity score: %d\n", similarity_score);

    free(left);
    free(right);

    return 0;
}