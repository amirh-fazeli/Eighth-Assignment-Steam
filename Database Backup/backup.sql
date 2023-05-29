PGDMP                         {            steam    15.2    15.2 
    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                        0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16420    steam    DATABASE     �   CREATE DATABASE steam WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE steam;
                postgres    false            �            1259    16431 	   downloads    TABLE     e   CREATE TABLE public.downloads (
    account_id text,
    game_id text,
    download_count integer
);
    DROP TABLE public.downloads;
       public         heap    postgres    false            �            1259    16421    games    TABLE     �   CREATE TABLE public.games (
    id text,
    title text,
    developer text,
    genre text,
    price double precision,
    release_year integer,
    controller_support boolean,
    reviews integer,
    size integer,
    file_path text
);
    DROP TABLE public.games;
       public         heap    postgres    false            �            1259    16426    users    TABLE     i   CREATE TABLE public.users (
    id text,
    username text,
    password text,
    date_of_birth date
);
    DROP TABLE public.users;
       public         heap    postgres    false            �          0    16431 	   downloads 
   TABLE DATA           H   COPY public.downloads (account_id, game_id, download_count) FROM stdin;
    public          postgres    false    216   �	       �          0    16421    games 
   TABLE DATA              COPY public.games (id, title, developer, genre, price, release_year, controller_support, reviews, size, file_path) FROM stdin;
    public          postgres    false    214   �	       �          0    16426    users 
   TABLE DATA           F   COPY public.users (id, username, password, date_of_birth) FROM stdin;
    public          postgres    false    215   �       �      x������ � �      �   @  x����r�0���)��A���-����1i;��E���#	'��W��6�8�3���wYd<IX�u�=a�}�Sw�X���X�b��ڑ� �� �!�)���ب���7֪]�P떅#�aM)D#U+ċ<J!
2G2Bl��ޔd�`S�+���q��<h������h\��W��jr��J9�[���U�n���RVG_�7q~������3>s���i̲ �T�d��@M70|z[]���eC�o?�X�yy i�����3/Ox>��wpsT5~���dW����Q��6�@t	�*�E5J&U�ķdS{nU��;��B?�W�c�Ꚗ]-OÓKFa8hbH�h�$5a�����Q��t~z���B��a��0y�0�x�~U�ܓ����
���j���~����}X�e���K ����������e]׷`��C��Ji�3���������|��C�������Q7��e[�p��}үX�7���O������b�'�����|R��8˳s�17X�F׵�/�XNF5XtT*?X�=vKnO�������^~M�L�M���D��j�X� �w��      �      x������ � �     