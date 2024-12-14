#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <regex.h>

int main() {
    FILE* file;
    char* line = NULL;
    size_t len = 0;
    regex_t regex;
    regmatch_t matches[3];
    const char* pattern = "mul\\(([0-9]+),([0-9]+)\\)";
    int sum = 0;

    if (regcomp(&regex, pattern, REG_EXTENDED) != 0) {
        fprintf(stderr, "Could not compile regex\n");
        return 1;
    }

    file = fopen("input.txt", "r");
    if (file == NULL) {
        perror("Error opening file");
        regfree(&regex);
        return 1;
    }

    while (getline(&line, &len, file) != -1) {
        char* cursor = line;

        while (regexec(&regex, cursor, 3, matches, 0) == 0) {
            char x_str[16], y_str[16];
            int x, y;

            int x_len = matches[1].rm_eo - matches[1].rm_so;
            int y_len = matches[2].rm_eo - matches[2].rm_so;
            strncpy(x_str, cursor + matches[1].rm_so, x_len);
            strncpy(y_str, cursor + matches[2].rm_so, y_len);
            x_str[x_len] = '\0';
            y_str[y_len] = '\0';

            // Convert X and Y to integers
            x = atoi(x_str);
            y = atoi(y_str);

            // Calculate the product and add to sum
            sum += x * y;

            // Move the cursor past the current match
            cursor += matches[0].rm_eo;
        }
    }

    free(line);
    fclose(file);
    regfree(&regex);

    // Print the result
    printf("Sum = %d", sum);

    return 0;
    //157676937
    //159833790
}
