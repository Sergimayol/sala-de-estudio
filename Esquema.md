# Esquema sala de estudio

## Estudiante

Repetir el esquema 1 vez por estudiante.

````pseudocode
si el director esta dentro:
  esperar para entrar

entrar sala de estudio

si el hay más estudiantes del permitido
  fiesta
  si el director está esperando
    avisar al director
sino
  se estudia

salir sala estudio

si soy le último
  aviso al director de que ya no hay nadie
`````

## Director

Repetir el esquema un número de rondas `N`.

````pseudocode
si no hay nadie en la sala
  entrar sala de estudio

si hay más estudiantes de lo permitido
  entrar sala de estudio

si hay estudiantes y son menos del permitido
  esperar hasta que no haya nadie
`````

