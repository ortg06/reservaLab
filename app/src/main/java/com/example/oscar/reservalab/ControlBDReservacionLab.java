package com.example.oscar.reservalab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ControlBDReservacionLab {
    private static final String[] camposAsignatura=new String[]
            {"codigoAsignatura","nombreAsignatura"};
    private static final String[] camposProfesor=new String[]
            {"idProfesor","nombreProfesor","idUsuario","idAsignacionCarga"};
    private static final String[] camposAsignacionCarga=new String[]
            {"idAsignacionCarga","codigoAsignatura","idCiclo"};


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String DROP_TABLE1="DROP TABLE IF EXISTS asignatura;";
    private static final String DROP_TABLE5="DROP TABLE IF EXISTS profesor;";
    private static final String DROP_TABLE12="DROP TABLE IF EXISTS asignacionCarga;";

    public ControlBDReservacionLab(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String BASE_DATOS = "reservacionLab.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context) {

            super(context, BASE_DATOS, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE asignatura(codigoAsignatura VARCHAR(10) NOT NULL PRIMARY KEY, nombreAsignatura VARCHAR(30) NOT NULL);");
                db.execSQL("CREATE TABLE profesor(idprofesor VARCHAR(10) NOT NULL PRIMARY KEY, nombreProfesor VARCHAR(30) NOT NULL,idUsuario VARCHAR(10) NOT NULL,idAsignacionCarga INTEGER NOT NULL);");
                db.execSQL("CREATE TABLE asignacionCarga(idAsignacionCarga INTEGER NOT NULL PRIMARY KEY, codigoAsignatura VARCHAR(10) NOT NULL, idCiclo INTEGER NOT NULL);");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        // public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
// }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE1);
                db.execSQL(DROP_TABLE5);
                db.execSQL(DROP_TABLE12);
                onCreate(db);
            } catch (Exception e) {
                //Message.message(context,""+e);
            }
        }
    }
        public void abrir() throws SQLException{

            db = DBHelper.getWritableDatabase();
            return;
        }

        public void cerrar(){
            DBHelper.close();
        }

        ///INSERTAR ASIGNATURA
         public String insertar(Asignatura asignatura){

            String regInsertados="Registro Insertado Nº=";
            long contador=0;
             //Verificar que no exista asignatura
             if(verificarIntegridad(asignatura,1))
             {
                 regInsertados="Error al Insertar el registros, Registro Duplicado. Verificar inserción";

             }
             else
             {
                 ContentValues asig = new ContentValues();
                 asig.put("codigoAsignatura", asignatura.getCodigoAsignatura());
                 asig.put("nombreAsignatura", asignatura.getNombreAsignatura());
                 contador=db.insert("asignatura", null, asig);
                 regInsertados=regInsertados+contador;
             }
             return  regInsertados;
         }

            //INSERTAR ASIGNACION CARGA
        public String insertar(AsignacionCarga asignacionCarga) {

            String regInsertados = "Registro Insertado Nº=";
            long contador = 0;

            //verificar que no exista la AsignacionCarga
            if (verificarIntegridad(asignacionCarga, 1)) {
                if (verificarIntegridad(asignacionCarga, 2)) {
                    regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
                } else {
                    ContentValues asigna = new ContentValues();
                    asigna.put("idAsignacionCarga", asignacionCarga.getIdAsignacionCarga());
                    asigna.put("codigoAsignatura", asignacionCarga.getCodigoAsignatura());
                    asigna.put("idCiclo", asignacionCarga.getIdCiclo());
                    contador = db.insert("asignacionCarga", null, asigna);

                }
            } else {
                regInsertados = "Error al insertar el registro sin referencias.Verificar Insercion";

            }
            regInsertados = regInsertados + contador;
            return regInsertados;

        }

        //INSERTAR PROFESOR
        public String insertar(Profesor profesor) {

            String regInsertados = "Registro Insertado Nº=";
            long contador = 0;
            //Verificar que no exista profesor
            if (verificarIntegridad(profesor, 1)) {
                regInsertados = "Error al Insertar el registros, Registro Duplicado. Verificar inserción";

            } else {
                ContentValues prof = new ContentValues();
                prof.put("idProfesor", profesor.getIdProfesor());
                prof.put("nombreProfesor", profesor.getNombreProfesor());
                prof.put("idUsuario", profesor.getIdUsuario());
                prof.put("idAsignacionCarga", profesor.getIdAsignacionCarga());
                contador = db.insert("asignatura", null, prof);
                regInsertados = regInsertados + contador;
            }
            return regInsertados;
        }

            //ACTUALIZAR ASIGNATURA

        public String actualizar(Asignatura asignatura){
             if(verificarIntegridad(asignatura,1)){
                 String[] id={asignatura.getCodigoAsignatura()};
                 ContentValues cv = new ContentValues();
                 cv.put("codigoAsignatura", asignatura.getCodigoAsignatura());
                 cv.put("nombreAsignatura", asignatura.getNombreAsignatura());
                 db.update("asignatura", cv, "codigoAsignatura=?", id);
                 return "Registro Actualizado Correctamente";
             }else{
                 return "Registro con código de Asignatura"+ asignatura.getCodigoAsignatura()+ "No existe";
             }
        }

        //ACTUALIZAR ASIGNACION CARGA
        public String actualizar(AsignacionCarga asignacionCarga){
            if(verificarIntegridad(asignacionCarga,1)){
                String[] id={Integer.toString(asignacionCarga.getIdAsignacionCarga())};
                ContentValues cv = new ContentValues();
                cv.put("idAsignacionCarga", asignacionCarga.getIdAsignacionCarga());
                cv.put("codigoAsignatura", asignacionCarga.getCodigoAsignatura());
                cv.put("idCiclo", asignacionCarga.getIdCiclo());
                db.update("asignacionCarga", cv, "idAsignacionCarga =?", id);
                return "Registro Actualizado Correctamente";
            }else{
                return "Registro con Id de Asignacion de Carga"+ asignacionCarga.getIdAsignacionCarga()+ "No existe";
            }
        }
        //ACTUALIZAR PROFESOR
        public String actualizar(Profesor profesor){
            if(verificarIntegridad(profesor,1)){
                String[] id={profesor.getIdProfesor()};
                ContentValues cv = new ContentValues();
                cv.put("idProfesor", profesor.getIdProfesor());
                cv.put("nombreProfesor", profesor.getNombreProfesor());
                cv.put("idUsuario", profesor.getIdUsuario());
                cv.put("idAsignacionCarga", profesor.getIdAsignacionCarga());
                db.update("profesor", cv, "idProfesor=?", id);
                return "Registro Actualizado Correctamente";
            }else{
                return "Registro con código de Profesor"+ profesor.getIdProfesor()+ "No existe";
            }
        }

        // Eliminar Asignatura
        public String eliminar(Asignatura asignatura){
            String regAfectados="Filas afectadas=";
            int contador=0;

            if(verificarIntegridad(asignatura,1)){
                if (verificarIntegridad(asignatura,1)){
                contador+=db.delete("asignatura","codigoAsignatura='"+asignatura.getCodigoAsignatura()+"'",null);
            }
            contador+=db.delete("asignatura","codigoAsignatura='"+asignatura.getCodigoAsignatura()+"'", null);
            regAfectados+=contador;
        }

            else{
                return "Registro con código de Asignatura" +asignatura.getCodigoAsignatura()+ "no existe";

            }

            return regAfectados;
    }
    //ELIMINAR ASIGNACION DE CARGA
    public String eliminar(AsignacionCarga asignacionCarga){
        String regAfectados="Filas afectadas=";
        int contador=0;

        if(verificarIntegridad(asignacionCarga,1)){
            if (verificarIntegridad(asignacionCarga,1)){
                contador+=db.delete("asignacionCarga","idAsignacionCarga='"+asignacionCarga.getIdAsignacionCarga()+"'",null);
            }
            contador+=db.delete("asignacionCarga","idAsignacionCarga='"+asignacionCarga.getIdAsignacionCarga()+"'", null);
            regAfectados+=contador;
        }

        else{
            return "Registro con Id de Asignacion" +asignacionCarga.getIdAsignacionCarga()+ "no existe";

        }

        return regAfectados;
    }


        //ELIMINAR PROFESOR
        public String eliminar(Profesor profesor){
            String regAfectados="Filas afectadas=";
            int contador=0;

            if(verificarIntegridad(profesor,1)){
                if (verificarIntegridad(profesor,1)){
                    contador+=db.delete("profesor","idProfesor='"+profesor.getIdProfesor()+"'",null);
                }
                contador+=db.delete("profesor","idProfesor='"+profesor.getIdProfesor()+"'", null);
                regAfectados+=contador;
            }

            else{
                return "Registro con Id de Profesor" +profesor.getIdProfesor()+ "no existe";

            }

            return regAfectados;
        }

    //CONSULTAR ASIGNATURA
        public Asignatura consultarAsignatura(String codigoAsignatura){

            String[] id={codigoAsignatura};
            Cursor cursor = db.query("asignatura",camposAsignatura,"codigoAsignatura=?",id,null,null,null);

            if(cursor.moveToFirst()){
                Asignatura asignatura = new Asignatura();
                asignatura.setCodigoAsignatura(cursor.getString(0));
                asignatura.setNombreAsignatura(cursor.getString(1));
                return asignatura;
            } else{
                return null;
            }

        }
        //CONSULTAR ASIGNACION DE CARGA
        public AsignacionCarga consultarAsignacionCarga(int idAsignacionCarga){

            String[] id={Integer.toString(idAsignacionCarga)};
            Cursor cursor = db.query("asignacionCarga",camposAsignacionCarga,"idAsignacionCarga=?",id,null,null,null);

            if(cursor.moveToFirst()){
                AsignacionCarga asignacionCarga = new AsignacionCarga();
                asignacionCarga.setIdAsignacionCarga(cursor.getInt(0));
                asignacionCarga.setCodigoAsignatura(cursor.getString(1));
                asignacionCarga.setIdCiclo(cursor.getInt(3));
                return asignacionCarga;
            } else{
                return null;
            }

        }
        //CONSULTAR PROFESOR
        public Profesor consultarProfesor(String idProfesor){

            String[] id={idProfesor};
            Cursor cursor = db.query("profesor",camposProfesor,"idProfesor=?",id,null,null,null);

            if(cursor.moveToFirst()){
                Profesor profesor = new Profesor();
                profesor.setIdProfesor(cursor.getString(0));
                profesor.setNombreProfesor(cursor.getString(1));
                profesor.setIdUsuario(cursor.getString(2));
                profesor.setIdAsignacionCarga(cursor.getInt(3));
                return profesor;
            } else{
                return null;
            }

        }

        private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{
            switch (relacion){
                case 1: //Verifica la existencia de asignatura
                {  Asignatura asignatura2= (Asignatura)dato;
                   String[] id = {asignatura2.getCodigoAsignatura()};
                   abrir();
                   Cursor c2 = db.query("asignatura", null, "codigoAsignatura=?", id, null,null,null);

                   if(c2.moveToFirst()){
                       //Se encontro Asignatura
                       return  true;
                   }
                   return false;
                }
                case 7: {//VERIFICAR QUE AL INSERTAR ASIGNACION EXISTA LA MATERIA Y EL CICLO
                    AsignacionCarga asignacionCarga = (AsignacionCarga) dato;
                    String[] id1 = {asignacionCarga.getCodigoAsignatura()};
                    String[] id2 = {Integer.toString(asignacionCarga.getIdCiclo())};
                    abrir();
                    Cursor cursor1 = db.query("asignatura", null, "codigoAsignatura =?", id1, null, null, null);
                    Cursor cursor2 = db.query("ciclo", null, "idciclo =?", id2, null, null, null);
                    if (cursor2.moveToFirst() && cursor1.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
                case 8:{
                    //verificar que al modificar la asignacion de carga existan la materia  y el ciclo
                    AsignacionCarga asignacionCarga1 = (AsignacionCarga)dato;
                    String[]  ids = {asignacionCarga1.getCodigoAsignatura(),Integer.toString(asignacionCarga1.getIdCiclo())};
                    abrir();
                    Cursor c = db.query("asignacionCarga",null, "codigoAsignatura = ? AND idCiclo =?",ids, null, null, null);
                    if(c.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
                case 9: {
                    //Verificar que al insertar el profesor exista el id usuario, y el id de asignacion de carga
                    Profesor profesor = (Profesor) dato;
                    String[] id3 = {profesor.getIdUsuario()};
                    String[] id4 = {Integer.toString(profesor.getIdAsignacionCarga())};
                    abrir();
                    Cursor cursor3 = db.query("usuario", null, "idUsuario =?", id3, null, null, null);
                    Cursor cursor4 = db.query("asignacionCarga", null, "idAsignacionCarga =?", id4, null, null, null);
                    if (cursor4.moveToFirst() && cursor3.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
                case 10:{
                    //Verificar que al modificar el maestro exista el usuario y la asignacion de carga
                    Profesor profesor1 = (Profesor) dato;
                    String[]  id5 = {profesor1.getIdUsuario(),Integer.toString(profesor1.getIdAsignacionCarga())};
                    abrir();
                    Cursor c = db.query("profesor",null, "idUsuario = ? AND idAsignacionCarga =?",id5, null, null, null);
                    if(c.moveToFirst()) {
                        return true;
                    }
                    return false;
                }
            default:
            return false;
        }

        }

      public String llenarBDReservacionLab(){

            //variables para asignatura VA
            final String[] VAcodigo={"PRN115", "HDP115", "COS115", "MEP115","ANS115"};
            final String[] VAnombre={"Progamacion 1", "Herramientas de Producctividad", "Comunicaciones", "Metodos probabilisticos", "Analisis Numerico"};
            //variables para AsignacionCarga VAC
            final Integer[] VACidAsignacionCarga = {1,2,3,4};
            final String[] VACcodigoAsignatura = {"PRN115", "HDP115", "COS115", "MEP115"};
            final Integer[] VACidCiclo = {12016,22017,12018,22018};
            //Variables para Profesor VP
            final String[] VPidProfesor = {"prof1","prof2","prof3","prof4"};
            final String[] VPnombreProfesor = {"Carlos","Maria","Lucia","Jonathan"};
            final String[] VPidUsuario = {"user1","user2","user3","user4"};
            final Integer[] VPidAsignacionCarga = {1,2,3,4};
            abrir();
            db.execSQL("DELETE FROM asignatura;");
            db.execSQL("DELETE FROM asignacionCarga;");
            db.execSQL("DELETE FROM profesor;");


            Asignatura asignatura = new Asignatura();
            for(int i=0; i<1; i++){
                asignatura.setCodigoAsignatura(VAcodigo[i]);
                asignatura.setNombreAsignatura(VAnombre[i]);
                insertar(asignatura);
            }
            AsignacionCarga asignacionCarga = new AsignacionCarga();
            for (int i=0; i<4; i++){
                asignacionCarga.setIdAsignacionCarga(VACidAsignacionCarga[i]);
                asignacionCarga.setCodigoAsignatura(VACcodigoAsignatura[i]);
                asignacionCarga.setIdCiclo(VACidCiclo[i]);
                insertar(asignacionCarga);
            }
            Profesor profesor = new Profesor();
            for (int i=0; i<4; i++){
                profesor.setIdProfesor(VPidProfesor[i]);
                profesor.setNombreProfesor(VPnombreProfesor[i]);
                profesor.setIdUsuario(VPidUsuario[i]);
                profesor.setIdAsignacionCarga(VPidAsignacionCarga[i]);
                insertar(profesor);
            }

            cerrar();
          return "Guardado correctamente";
      }
    }


