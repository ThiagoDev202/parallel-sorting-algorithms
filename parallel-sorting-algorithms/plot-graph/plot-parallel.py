import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import ScalarFormatter

def read_data(file_path):
    """Função para ler dados do arquivo CSV."""
    return pd.read_csv(file_path)

def plot_data(data_frames, algorithm_names):
    """Função para plotar os dados em gráfico de barras com escala linear."""
    fig, axes = plt.subplots(nrows=2, ncols=2, figsize=(14, 10))
    axes = axes.flatten()

    for ax, algoritmo in zip(axes, algorithm_names):
        threads = [1, 2, 4, 6, 7]
        tempos = [df[df['Algoritmo'] == algoritmo]['Tempo de Execução (ms)'].values[0] for df in data_frames]

        ax.bar(threads, tempos)
        ax.set_title(f'{algoritmo}')
        ax.set_xlabel('Número de Threads')
        ax.set_ylabel('Tempo de Execução (ms)')
        ax.set_xticks(threads)
        
        # Formatação para não usar notação científica
        formatter = ScalarFormatter(useOffset=False)
        formatter.set_scientific(False)
        ax.yaxis.set_major_formatter(formatter)

    fig.suptitle('Comparativo dos Tempos de Execução para Diferentes Números de Threads')
    plt.tight_layout(rect=[0, 0.03, 1, 0.95])
    plt.show()

def main():
    """Função principal para executar a leitura e plotagem dos dados."""
    file_paths = [
        r'C:\plot\plot-graph\test_parallel\parallel_1_threads.csv',
        r'C:\plot\plot-graph\test_parallel\parallel_2_threads.csv',
        r'C:\plot\plot-graph\test_parallel\parallel_4_threads.csv',
        r'C:\plot\plot-graph\test_parallel\parallel_6_threads.csv',
        r'C:\plot\plot-graph\test_parallel\parallel_7_threads.csv'
    ]

    data_frames = [read_data(fp) for fp in file_paths]
    algorithm_names = data_frames[0]['Algoritmo']. unique()
    plot_data(data_frames, algorithm_names)

if __name__ == "__main__":
    main()
