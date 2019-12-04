CREATE DATABASE esperancateste
	WITH OWNER = postgres
	ENCODING = 'UTF8'
	TABLESPACE = pg_default
	LC_COLLATE = 'Portuguese_Brazil.1252'
	LC_CTYPE = 'Portuguese_Brazil.1252'
	CONNECTION LIMIT = -1;
	   
CREATE TABLE permissao
(
  id bigserial NOT NULL,
  nome character varying(255) NOT NULL,
  CONSTRAINT permissao_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE endereco
(
  id bigserial NOT NULL,
  cidade character varying(255) NOT NULL,
  setor character varying(255) NOT NULL,
  rua character varying(255),
  quadra character varying(255),
  lote character varying(255),
  numero character varying(10),
  cep character varying(9),
  uf character varying(2) NOT NULL DEFAULT 'GO'::character varying,
  CONSTRAINT endereco_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE membro
(
  id bigserial NOT NULL,
  nome character varying(255) NOT NULL,
  cpf character varying(14),
  telefone character varying(14),
  email character varying(255),
  sexo boolean NOT NULL DEFAULT false,
  tipo character varying(255) NOT NULL,
  endereco_id bigint,
  situacao character varying(255) NOT NULL,
  data_nascimento date,
  data_inclusao timestamp with time zone,
  CONSTRAINT membro_pkey PRIMARY KEY (id),
  CONSTRAINT membro_endereco_id_fkey FOREIGN KEY (endereco_id)
      REFERENCES endereco (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT membro_cpf_key UNIQUE (cpf)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE usuario
(
  id bigserial NOT NULL,
  login character varying(255) NOT NULL,
  nome character varying(255) NOT NULL,
  senha character varying(255) NOT NULL,
  ativo boolean NOT NULL DEFAULT true,
  data_inclusao timestamp with time zone NOT NULL,
  membro_id bigint,
  CONSTRAINT usuario_pkey PRIMARY KEY (id),
  CONSTRAINT usuario_membro_id_fkey FOREIGN KEY (membro_id)
      REFERENCES membro (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

CREATE TABLE usuario_permissao
(
  usuario_id bigint NOT NULL,
  permissao_id bigint NOT NULL,
  CONSTRAINT usuario_permissao_pkey PRIMARY KEY (usuario_id, permissao_id),
  CONSTRAINT usuario_permissao_permissao_id_fkey FOREIGN KEY (permissao_id)
      REFERENCES permissao (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT usuario_permissao_usuario_id_fkey FOREIGN KEY (usuario_id)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);