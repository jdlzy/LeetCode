import matplotlib.pyplot as plt
import numpy as np

print("hello")
plt.style.use('dark_background')
fig, ax = plt.subplots()

x = np.array([1, 2, 3, 4, 5, 4, 5])
# x = [1, 2, 3, 4, 5, 4, 5]
y = x * x
plt.bar(x, y, color='r')
for a, b in zip(x, y):
    plt.text(a, b + 0.2, '%d' % b, ha='center', va='bottom', fontsize=20)
plt.show()



