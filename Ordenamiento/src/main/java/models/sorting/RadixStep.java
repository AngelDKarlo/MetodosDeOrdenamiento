package models.sorting;

/**
 * Representa un único paso en el algoritmo Radix Sort.
 * @param arrayState El estado actual del arreglo.
 * @param currentExp El exponente (1, 10, 100...) usado para la pasada actual.
 * @param isFinal Un booleano que indica si este es el estado final y ordenado.
 */
public record RadixStep(int[] arrayState, int currentExp, boolean isFinal) {}