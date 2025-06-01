Practica 1: Programación Paralela y Concurrente

Descripción

Este proyecto implementa la suma de un arreglo de 1,000,000 números enteros aleatorios (entre 1 y 10,000) de forma secuencial y paralela utilizando hilos virtuales de Java 21, 

Clonar el repositorio:
git clone https://github.com/ErnestoDelVillar/Practica1Paralela.git

Abrir en IntelliJ IDEA:
Selecciona File > Open y elige la carpeta Practica1Paralela.
Configura el JDK 21 en File > Project Structure > Project > Project SDK.

Compilar y ejecutar:
Haz clic derecho en Main.java en el Project Explorer y selecciona Run 'Main.main()'.

Ejemplo de Entrada y Salida
no se introduce nada

una salida se deberia ver asi: 

| Threads | tiempo de ejecucion | tiempo en paralelo | Speedup | eficiencia |
|---------|---------------------|--------------------|---------|------------|
| 1       | 0.1234              | -                  | 1.0000  | 1.0000     |
| 2       | 0.1234              | 0.0678             | 1.8198  | 0.9099     |
| 4       | 0.1234              | 0.0456             | 2.7061  | 0.6765     |
| 8       | 0.1234              | 0.0389             | 3.1722  | 0.3965     |
| 16      | 0.1234              | 0.0365             | 3.3781  | 0.2111     |
| 32      | 0.1234              | 0.0352             | 3.5057  | 0.1095     |

