cmake_minimum_required(VERSION 3.13)
project(proj C)

set(CMAKE_C_STANDARD 99)

add_executable(proj main.c estado.c estado.h stack.c stack.h comando.c comando.h interpretador.c interpretador.h bot.c bot.h)