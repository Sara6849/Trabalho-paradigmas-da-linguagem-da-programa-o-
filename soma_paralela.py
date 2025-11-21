import threading
import time
import random

class Worker(threading.Thread):
    def __init__(self, vetor, inicio, fim):
        super().__init__()
        self.vetor = vetor
        self.inicio = inicio
        self.fim = fim
        self.soma_local = 0

    def run(self):
        for i in range(self.inicio, self.fim):
            self.soma_local += self.vetor[i]

def soma_paralela(vetor, num_threads):
    tamanho = len(vetor)
    threads = []
    chunk = (tamanho + num_threads - 1) // num_threads

    for t in range(num_threads):
        inicio = t * chunk
        fim = min(inicio + chunk, tamanho)
        if inicio >= fim:
            break

        worker = Worker(vetor, inicio, fim)
        threads.append(worker)
        worker.start()

    total = 0
    for th in threads:
        th.join()
        total += th.soma_local

    return total

if __name__ == "__main__":
    vetor = [random.randint(1, 100) for _ in range(2_000_000)]
    inicio = time.time()
    resultado = soma_paralela(vetor, 4)
    fim = time.time()

    print("Soma paralela =", resultado)
    print("Tempo =", fim - inicio, "s")
    print("Soma sequencial =", sum(vetor))
