CREATE TABLE players
(
   pseudo text NOT NULL, 
   points integer NOT NULL, 
   CONSTRAINT "Primary" PRIMARY KEY (pseudo)
) 
WITH (
  OIDS = FALSE
)
;
ALTER TABLE players OWNER TO ticket;
