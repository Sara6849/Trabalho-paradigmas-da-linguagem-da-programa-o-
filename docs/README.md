# Trabalho-paradigmas-da-linguagem-da-programa-o-
Resolução dos problemas:  Soma de vetores com threads. Contador sincronizado. 

Usou-se os pronts de comando no chat GPT 

Pronnt (Soma paralela)

"Escreva um código Java que divida um vetor de inteiros entre N threads, cada uma calcule a soma parcial e o main agregue as somas. Use ExecutorService e Callable. Inclua um main para testar com um vetor grande e compare com soma sequencial."

Pronpt (contador sincornzado)

"Mostre um exemplo em Java que demonstre race condition ao incrementar um contador por muitas threads e duas soluções (usando synchronized e AtomicInteger). Inclua saída mostrando diferença entre inseguro e seguro."


Referencias 

SILVEIRA, S. R.; et al. Paradigmas de programação: Uma introdução. Capítulo 5 – Programação Concorrente em Java. (pág. 75–91). PDF fornecido.

DEITEL, H.; DEITEL, P. Java - How to Program (referenciado no capítulo). (usado como base conceitual).

Códigos gerados com auxílio de IA (ChatGPT) e adaptados manualmente. 

O atomicInteger serve:
 Para não deixar as trheds se sobrescreverem, e trabalhar uma de cada vez.