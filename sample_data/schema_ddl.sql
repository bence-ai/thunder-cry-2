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

-- DROP TABLE IF EXISTS public.spell;
-- CREATE TABLE public.spell (
--   id serial NOT NULL PRIMARY KEY,
--   name text NOT NULL
-- );
--
-- DROP TABLE IF EXISTS public.player_spell;
-- CREATE TABLE public.player_spell (
--   player_id integer NOT NULL ,
--   spell_id integer NOT NULL
-- );

INSERT INTO game_state
VALUES (DEFAULT, 'test', 2, '1976-06-22 19:10:25-07', 1);

INSERT INTO player
VALUES (DEFAULT, 'Test_Hero', 'BROWN_BOY', 180, 350, 'SWORD', 30);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

-- ALTER TABLE ONLY public.player_spell
--     ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);
--
-- ALTER TABLE ONLY public.player_spell
--     ADD CONSTRAINT fk_spell_id FOREIGN KEY (spell_id) REFERENCES public.spell(id);
--
-- INSERT INTO spell VALUES (DEFAULT, 'Fire');
-- INSERT INTO spell VALUES (DEFAULT, 'Thunder');
-- INSERT INTO spell VALUES (DEFAULT, 'Blizzard');
-- INSERT INTO spell VALUES (DEFAULT, 'Meteor');
-- INSERT INTO spell VALUES (DEFAULT, 'Flesh');
-- INSERT INTO spell VALUES (DEFAULT, 'Greater');
-- INSERT INTO spell VALUES (DEFAULT, 'Zombie');
-- INSERT INTO spell VALUES (DEFAULT, 'Shadow');
-- INSERT INTO spell VALUES (DEFAULT, 'Curse');

