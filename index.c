#include <stdio.h>
#include <stdlib.h>

// testing comments

int main() {
    FILE* file = fopen("index.mgf", "r");
    if(file) {
        fseek(file, 0 , SEEK_END);
        long length = ftell(file);
        fseek(file, 0, SEEK_SET);

        char* buffer = malloc((length + 1) * sizeof(char));
        if(buffer) {
            fread(buffer, sizeof(char), length, file);
            buffer[length] = '\0';
        }

        fclose(file);

        printf("%s", buffer);
    
        FILE* target = fopen("index.c", "w");
        fputs(buffer, target);
        fclose(target);

        free(buffer);
    }

    return 0;
}lateyInstall=C:\ProgramData\ch