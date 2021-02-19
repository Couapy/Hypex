# Maps

The maps are defined by points, and segments (defined by two points);

To create a map, you must create points, then link the points to create walls.

> IMPORTANT: Add frame all around the map must be created.

There one line per point, segment or origin.

The maps are located in the `resources/maps` folder as `.txt` files.

## Points

Point lines are defined by the following nomenclature :

```text
p:x;y
```

## Segments

Segment line are defined by the following nomenclature :

```text
s:numberPoint1;numberPoint2
```

## Origins

Origin is defined by the following nomenclature :

```text
o:x;y
```

## Examples

For example :

```text
o:50;150
p:0;0
p:300;0
p:300;300
p:0;300
s:0;1
s:1;2
s:2;3
s:3;0
```
