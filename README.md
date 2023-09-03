# resilience4j-prometheus-grafana
resilience4j prometheus grafana

## Circuit Breaker:

![image](https://github.com/martinachov/resilience4j-prometheus-grafana/assets/16406047/58d29d38-cbec-40d9-931c-e14dc27c59fc)

El estado del CircuitBreaker cambia de CLOSED a OPEN cuando la tasa de fallas es igual o mayor que un umbral configurable. Por ejemplo, cuando más del 50% de las llamadas registradas han fallado.

De forma predeterminada, todas las excepciones se consideran fallas. Puede definir una lista de excepciones que deben considerarse fallas. Todas las demás excepciones se cuentan como un éxito, a menos que se ignoren. Las excepciones también se pueden ignorar para que no se consideren ni fallas ni éxitos.

El CircuitBreaker también cambia de CLOSED a OPEN cuando el porcentaje de llamadas lentas es igual o mayor que un umbral configurable. Por ejemplo, cuando más del 50% de las llamadas registradas tardaron más de 5 segundos. Esto ayuda a reducir la carga en un sistema externo antes de que sea realmente no responda.

La tasa de fallas y la tasa de llamadas lentas solo se pueden calcular si se registraron un número mínimo de llamadas. Por ejemplo, si el número mínimo de llamadas requeridas es 10, entonces se deben registrar al menos 10 llamadas antes de que se pueda calcular la tasa de fallas. Si solo se han evaluado 9 llamadas, el CircuitBreaker no se abrirá, incluso si las 9 llamadas han fallado.

El CircuitBreaker rechaza las llamadas con una excepción CallNotPermittedException cuando está OPEN. Después de que haya transcurrido un período de tiempo de espera, el estado del CircuitBreaker cambia de OPEN a HALF-OPEN y permite un número configurable de llamadas para ver si el backend aún está indisponible o ha vuelto a estar disponible. Las llamadas adicionales se rechazan con una excepción CallNotPermittedException, hasta que se completen todas las llamadas permitidas.

Si la tasa de fallas o la tasa de llamadas lentas es entonces igual o mayor que el umbral configurado, el estado cambia a OPEN. Si la tasa de fallas y la tasa de llamadas lentas es inferior al umbral, el estado cambia a CLOSED.

### El CircuitBreaker es thread-safe:

- El estado de un CircuitBreaker se almacena en un AtomicReference.

- El CircuitBreaker utiliza operaciones atómicas para actualizar el estado con funciones sin efectos secundarios.

- La grabación de llamadas y la lectura de instantáneas de la Ventana Deslizante se sincronizan.


Eso significa que se debe garantizar la atomicidad y solo un hilo puede actualizar el estado o la Ventana Deslizante en un momento dado.

Sin embargo, el CircuitBreaker no sincroniza la llamada a la función. Eso significa que la llamada a la función en sí no es parte de la sección crítica. De lo contrario, un CircuitBreaker introduciría una gran penalización de rendimiento y cuello de botella. Una llamada a función lenta tendría un impacto negativo considerable en el rendimiento general.

Si 20 hilos concurrentes solicitan permiso para ejecutar una función y el estado del CircuitBreaker está cerrado, todos los hilos están permitidos invocar la función. Incluso si el tamaño de la ventana deslizante es 15. La ventana deslizante no significa que solo se permiten 15 llamadas para ejecutarse de forma concurrente.
