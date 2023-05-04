# WhileParser
El proyecto consiste en construir un sencillo reconocedor de enunciados while bien formados del lenguaje Kotlin en su versión 1.8.10. El programa debe leer los datos de entrada, un conjunto de bloques while sintácticamente correctos y debe arrojar como resultado la aceptación o rechazo del conjunto de bloques como correcto

## Especificaciones generales
- El programa debe leer los datos de entrada de un archivo, el cual se le pasa por medio de la línea de comandos o se carga por medio de una ventana de dialogo de abrir archivo.
- El programa debe utilizar expresiones regulares para separar el flujo de datos de la entrada en tokens (Ejemplo: while, x, <, 5,  {, while, }, etc.). Puedes usar los paquetes o bibliotecas estándar de expresiones regulares de Python o Java.
- Tu programa debe reconocer enunciados while bien formados del lenguaje Kotlin. Checa los ejemplos al final de esta especificacion.
- Tu programa debe implementar un autómata de pila para parsear la entrada (divida en tokens de forma previa con expresiones regulares) y validar si en efecto se trata de bloques de ciclos while bien formados.
- NO SE PERMITE utilizar generadores de parseadores existentes, de ningun tipo y de ningun lenguaje. Si lo haces, tu proyecto automaticamente se evalua a 0.
- Cada enunciado while debe contener, por supuesto una expresion booleana. Considera unicamente expresiones booleanas con las siguientes caracteristicas:
Variables de una sola letra ([a-z])
Constantes de un solo digito ([0-9])
Operadores relacionales: <, >, ==, >=, <=, !=
- La salida del programa debe contener los siguientes datos:
Numero total de variables (diferentes) usadas en todos los while encontrados.
Numero total de operadores de comparación encontrados (con repeticiones).
Numero total de while's que contienen los bloques parseados.
- El formato de la salida es a libre elección, pero debe ser claro.
