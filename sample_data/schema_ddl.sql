DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    name text NOT NULL,
    current_map text NOT NULL,
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id integer NOT NULL
);

DROP TABLE IF EXISTS public.player;
CREATE TABLE public.player (
    id serial NOT NULL PRIMARY KEY,
    name text NOT NULL,
    avatar text NOT NULL,
    mp integer NOT NULL,
    hp integer NOT NULL,
    weapon text NOT NULL,
    defense integer NOT NULL
);

INSERT INTO game_state
VALUES (DEFAULT, 'test', 2, '1976-06-22 19:10:25-07', 1);

INSERT INTO player
VALUES (DEFAULT, 'Test_Hero', 'BROWN_BOY', 180, 350, 'SWORD', 30);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);
