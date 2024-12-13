#include <stdio.h>
#include <stdlib.h>

int compare(const void *a, const void *b) {
    return (*(int *)a - *(int *)b);
}

int main() {
    // Open the input file
    FILE *file = fopen("input.sql", "r");
    if (file == NULL) {
        perror("Unable to open file");
        return 1;
    }

    // Determine the number of lines in the file
    int lines = 0;
    char ch;
    while ((ch = fgetc(file)) != EOF) {
        if (ch == '\n') {
            lines++;
        }
    }

    // Reset file pointer to the beginning
    rewind(file);

    // Dynamically allocate memory for the arrays based on the number of lines
    int *left = (int *)malloc(lines * sizeof(int));
    int *right = (int *)malloc(lines * sizeof(int));
    if (left == NULL || right == NULL) {
        perror("Memory allocation failed");
        fclose(file);
        return 1;
    }

    // Read the input file and store the numbers into arrays
    int n = 0;
    while (fscanf(file, "%d %d", &left[n], &right[n]) == 2) {
        n++;
    }

    fclose(file);

    // Sort both lists
    qsort(left, n, sizeof(int), compare);
    qsort(right, n, sizeof(int), compare);

    // Calculate the total distance
    int total_distance = 0;
    for (int i = 0; i < n; i++) {
        total_distance += abs(left[i] - right[i]);
    }

    // Print the total distance
    printf("Total distance: %d\n", total_distance);

    // Free the allocated memory
    free(left);
    free(right);

    return 0;
}