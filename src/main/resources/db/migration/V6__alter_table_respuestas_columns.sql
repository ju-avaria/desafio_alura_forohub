ALTER TABLE respuestas
CHANGE COLUMN esLaSolucion es_la_solucion TINYINT(1) DEFAULT 0,
CHANGE COLUMN fueBorrado fue_borrado TINYINT(1) DEFAULT 0;
