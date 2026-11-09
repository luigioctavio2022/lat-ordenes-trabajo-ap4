# Modelo ampliado normalizado propuesto

Siete tablas y siete FK. No es el esquema integrado con Java.

- zonas → lineas: padre 0..* hijas; hija 1..1 padre; FK lineas.id_zona NOT NULL.
- lineas → piquetes: padre 0..* hijas; hija 1..1 padre; FK piquetes.id_linea NOT NULL.
- piquetes → activos: padre 0..* hijas; hija 1..1 padre; FK activos.id_piquete NOT NULL.
- usuarios → ordenes_trabajo: padre 0..* hijas; hija 1..1 padre; FK ordenes_trabajo.id_operario_responsable NOT NULL.
- lineas → ordenes_trabajo: padre 0..* hijas; hija 1..1 padre; FK ordenes_trabajo.id_linea NOT NULL.
- ordenes_trabajo → detalle_ot_activos: padre 0..* hijas; hija 1..1 padre; FK detalle_ot_activos.id_ot NOT NULL.
- activos → detalle_ot_activos: padre 0..* hijas; hija 1..1 padre; FK detalle_ot_activos.id_activo NOT NULL.

Se elimina id_zona de la orden: se deriva de su línea. Los estados son NOT NULL con DEFAULT. La relación orden-activo es N:M opcional en ambos lados, resuelta con detalle y PK compuesta. La correspondencia entre activo y línea debe validarse antes de una implantación.
