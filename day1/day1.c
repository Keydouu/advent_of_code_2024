#include <stdio.h>
#include <stdlib.h>

int compare(const void *a, const void *b) {
    return (*(int *)a - *(int *)b);
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

    int total_distance = 0;
    for (int i = 0; i < n; i++) {
        total_distance += abs(left[i] - right[i]);
    }

    printf("Total distance: %d\n", total_distance);

    free(left);
    free(right);

    return 0;
}