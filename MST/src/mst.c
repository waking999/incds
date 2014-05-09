#include<stdio.h>
#define INFINITE 999
int p[10];


void kruskal(int w[10][10], int n) {
	int min, sum = 0, ne = 0, i, j, u, v, a, b;
	char name[10] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
	for (i = 1; i <= n; i++){
		p[i] = 0;
	}
	while (ne < n - 1) {
		min = INFINITE;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				if (w[i][j] < min) {
					min = w[i][j];
					u = a = i;
					v = b = j;
				}
			}
		}
		while (p[u]) {
			u = p[u];
		}
		while (p[v]) {
			v = p[v];
		}
		if (u != v) {
			ne++;
			sum += min;
			printf("\n edge %c --> %c is %d", name[a], name[b], min);
			p[v] = u;
		}
		w[a][b] = w[b][a] = INFINITE;
	}
	printf("\n min cost spanning tree= %d", sum);
}

int main(void) {
	int n, i, j;
	n = 10;
	int w[10][10] = { { 0, 4, 0, 3, 0, 0, 0, 0, 0, 0 }, { 4, 0, 2, 5, 4, 0, 0,
			0, 0, 0 }, { 0, 2, 0, 0, 3, 0, 0, 4, 0, 0 }, { 3, 5, 0, 0, 4, 3, 3,
			0, 0, 0 }, { 0, 4, 3, 4, 0, 0, 4, 2, 0, 0 }, { 5, 0, 0, 3, 0, 0, 2,
			0, 3, 0 }, { 0, 0, 0, 3, 4, 2, 0, 3, 0, 3 }, { 0, 0, 4, 0, 2, 0, 3,
			0, 3, 4 }, { 0, 0, 0, 0, 0, 3, 0, 3, 0, 2 }, { 0, 0, 0, 0, 0, 0, 3,
			4, 2, 0 } };

	for (i = 0; i < n; i++)
		for (j = 0; j < n; j++)
			if (w[i][j] == 0) {
				w[i][j] = INFINITE;
			}
	kruskal(w, n);
	return 0;
}
