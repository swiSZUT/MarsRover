CREATE TABLE IF NOT EXISTS public.game (
    gameId INTEGER PRIMARY KEY,
    rover_x INTEGER NOT NULL,
    rover_y INTEGER NOT NULL,
    rover_orientation INTEGER NOT NULL,
    width INTEGER NOT NULL,
    height INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS public.slopes (
    x INTEGER,
    y INTEGER,
    slope INTEGER,
    PRIMARY KEY (x, y)
);