import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import ScalarFormatter

# Definindo os caminhos dos arquivos
file_paths = [
    r'C:\plot\plot-graph\test_sorting\sorting_1000.csv',
    r'C:\plot\plot-graph\test_sorting\sorting_10k.csv',
    r'C:\plot\plot-graph\test_sorting\sorting_50k.csv',
    r'C:\plot\plot-graph\test_sorting\sorting_100k.csv',
    r'C:\plot\plot-graph\test_sorting\sorting_200k.csv',
]

# Função para ler os dados
def read_data(file_path):
    return pd.read_csv(file_path)

# Lendo os dados dos arquivos
data_frames = [read_data(fp) for fp in file_paths]

# Extrair os tamanhos dos arrays a partir dos nomes dos arquivos para usar como eixo X
array_sizes = [1000, 10000, 50000, 100000, 200000]
array_size_labels = ['1k', '10k', '50k', '100k', '200k']
colors = ['blue', 'green', 'red', 'cyan', 'purple']  # Mudança de cor rosa para vermelho

# Preparar os dados para plotagem
algorithms = data_frames[0]['Algorithm'].unique()

# Criando um subplot para cada algoritmo
fig, axes = plt.subplots(nrows=2, ncols=2, figsize=(15, 10))
axes = axes.flatten()

for ax, algorithm in zip(axes, algorithms):
    for i, (df, size) in enumerate(zip(data_frames, array_sizes)):
        execution_time = df[df['Algorithm'] == algorithm]['Execution Time (ms)'].values[0]
        ax.bar(i, execution_time, color=colors[i], label=f'{size} elements', width=0.8)  # Ajuste do espaçamento entre as barras
    
    ax.set_title(f'Time Complexity of {algorithm}')
    ax.set_ylabel('Execution Time (ms)')
    ax.set_xticks(range(len(array_sizes)))
    ax.set_xticklabels(array_size_labels)
    ax.legend()

fig.suptitle('Comparative Execution Times by Array Size for Different Algorithms')
fig.tight_layout()
plt.show()
