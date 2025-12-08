package utils

data class MapItem<T>(val x: Int, val y: Int, val item: T)

sealed class Map2d<T>(val data: Array<Array<T>>) {
    class CharMap2d(input: List<String>) : Map2d<Char>(
        input.map { it.toCharArray().toTypedArray() }.toTypedArray()
    )

    class IntMap2d(input: List<String>) : Map2d<Int>(
        input.map { it.toCharArray().map { it.digitToInt() }.toTypedArray() }.toTypedArray()
    )

    class BooleanMap2d(input: List<String>) : Map2d<Boolean>(
        input.map { it.toCharArray().map { false }.toTypedArray() }.toTypedArray()
    )

    val ySize = data.size
    val xSize = data[0].size
    val allSize = xSize * ySize

    operator fun get(x: Int, y: Int) = data[y][x]

    fun getValueByIndex(index: Int): T {
        if (index !in 0..<allSize) throw IndexOutOfBoundsException("Index: $index")
        val y = index / xSize
        val x = index % xSize
        return get(x, y)
    }

    fun getItemByIndex(index: Int): MapItem<T> {
        if (index !in 0..<allSize) throw IndexOutOfBoundsException("Index: $index")
        val y = index / xSize
        val x = index % xSize
        return MapItem(x, y, get(x, y))
    }

    fun getOrNull(x: Int, y: Int) = when (x < xSize &&  y < ySize && x >=0 && y >= 0) {
        true -> data[y][x]
        false -> null
    }

    operator fun set(x: Int, y: Int, value: T) {
        data[y][x] = value
    }

    fun forEachIndexed(block: (y: Int, x: Int, item: T) -> Unit) {
        for (y in 0..<ySize) {
            for (x in 0..<xSize) {
                block(y, x, data[y][x])
            }
        }
    }

    inline fun <R> mapValues(transform: (y: Int, x: Int, item: T) -> T) {
        for (y in 0..<ySize) {
            for (x in 0..<xSize) {
                data[y][x] = transform(y, x, data[y][x])
            }
        }
    }

    inline fun updateValues(transform: (y: Int, x: Int, item: T) -> T) {
        for (y in 0..<ySize) {
            for (x in 0..<xSize) {
                data[y][x] = transform(y, x, data[y][x])
            }
        }
    }

    inline fun <R> mapIndexed(transform: (y: Int, x: Int, item: T) -> R): List<R> {
        val items = mutableListOf<R>()
        for (y in 0..<ySize) {
            for (x in 0..<xSize) {
                items.add(transform(y, x, data[y][x]))
            }
        }
        return items
    }

    fun forEach(block: (item: T) -> Unit) {
        for (y in 0..<ySize) {
            for (x in 0..<xSize) {
                block(data[y][x])
            }
        }
    }

    fun valueIterable(): Iterable<T> {
        return object : Iterable<T> {
            override fun iterator(): Iterator<T> {
                return valueIterator()
            }
        }
    }
    fun valueIterator(): Iterator<T> {
        return object : Iterator<T> {
            protected var currentIndex = 0

            override fun hasNext(): Boolean {
                return currentIndex < allSize
            }

            override fun next(): T {
                return getValueByIndex(currentIndex++)
            }
        }
    }

    fun itemIterable(): Iterable<MapItem<T>> {
        return object : Iterable<MapItem<T>> {
            override fun iterator(): Iterator<MapItem<T>> {
               return itemIterator()
            }
        }
    }
    fun itemIterator(): Iterator<MapItem<T>> {
        return object : Iterator<MapItem<T>> {
            protected var currentIndex = 0

            override fun hasNext(): Boolean {
                return currentIndex < allSize
            }

            override fun next(): MapItem<T> {
                return getItemByIndex(currentIndex++)
            }
        }
    }

    fun forEachItem(block: (item: MapItem<T>) -> Unit) {
        for (y in 0..<ySize) {
            for (x in 0..<xSize) {
                block(MapItem(x, y, data[y][x]))
            }
        }
    }

    fun isValidCoordinate(x: Int, y: Int) = x in 0..<xSize && y in 0..<ySize

    fun north(item: MapItem<T>) = elementUp(item)
    fun elementUp(item: MapItem<T>) = elementUp(item.x, item.y)
    fun elementUp(x: Int, y: Int) = when (y <= 0) {
        true -> null
        else -> MapItem(x, y - 1, data[y - 1][x])
    }

    fun south(item: MapItem<T>) = elementDown(item)
    fun elementDown(item: MapItem<T>) = elementDown(item.x, item.y)
    fun elementDown(x: Int, y: Int) = when (y >= ySize - 1) {
        true -> null
        else -> MapItem(x, y + 1, data[y + 1][x])
    }

    fun west(item: MapItem<T>) = elementLeft(item)
    fun elementLeft(item: MapItem<T>) = elementLeft(item.x, item.y)
    fun elementLeft(x: Int, y: Int) = when (x <= 0) {
        true -> null
        else -> MapItem(x - 1, y, data[y][x - 1])
    }

    fun east(item: MapItem<T>) = elementRight(item)
    fun elementRight(item: MapItem<T>) = elementRight(item.x, item.y)
    fun elementRight(x: Int, y: Int) = when (x >= xSize - 1) {
        true -> null
        else -> MapItem(x + 1, y, data[y][x + 1])
    }

    fun northEast(item: MapItem<T>) = elementUpLeft(item)
    fun elementUpLeft(item: MapItem<T>) = elementUpLeft(item.x, item.y)
    fun elementUpLeft(x: Int, y: Int) = when (y <= 0) {
        true -> null
        else -> when (x <= 0) {
            true -> null
            else -> MapItem(x - 1, y - 1, data[y - 1][x - 1])
        }
    }

    fun northWest(item: MapItem<T>) = elementUpRight(item)
    fun elementUpRight(item: MapItem<T>) = elementUpRight(item.x, item.y)
    fun elementUpRight(x: Int, y: Int) = when (y <= 0) {
        true -> null
        else -> when (x >= xSize - 1) {
            true -> null
            else -> MapItem(x + 1, y - 1, data[y - 1][x + 1])
        }
    }

    fun southEast(item: MapItem<T>) = elemenDownLeft(item)
    fun elemenDownLeft(item: MapItem<T>) = elemenDownLeft(item.x, item.y)
    fun elemenDownLeft(x: Int, y: Int) = when (y >= ySize - 1) {
        true -> null
        else -> when (x <= 0) {
            true -> null
            else -> MapItem(x - 1, y + 1, data[y + 1][x - 1])
        }
    }

    fun southWest(item: MapItem<T>) = elemenDownRight(item)
    fun elemenDownRight(item: MapItem<T>) = elemenDownRight(item.x, item.y)
    fun elemenDownRight(x: Int, y: Int) = when (y >= ySize - 1) {
        true -> null
        else -> when (x >= xSize - 1) {
            true -> null
            else -> MapItem(x + 1, y + 1, data[y + 1][x + 1])
        }
    }

    fun allSides(item: MapItem<T>, diagonal: Boolean = false): List<MapItem<T>?> = when (diagonal) {
        true -> listOf(north(item), northEast(item), east(item), southEast(item), south(item), southWest(item), west(item), northWest(item))
        false -> listOf(north(item), east(item), south(item), west(item))
    }

    fun buildPath(path: MutableList<MapItem<T>>, nextCandidates: () -> List<MapItem<T>>) {
        nextCandidates().forEach { candidate ->
            buildPath(path.also { it.add(candidate) }, nextCandidates)
        }
    }
}