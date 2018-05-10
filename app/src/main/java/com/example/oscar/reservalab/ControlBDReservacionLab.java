package com.example.oscar.reservalab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;

public class ControlBDReservacionLab {
    private static final String[] camposAsignatura=new String[]
            {"codigoAsignatura","nombreAsignatura"};


    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String DROP_TABLE1="DROP TABLE IF EXISTS asignatura;";

    public ControlBDReservacionLab (Context ctx){
        this.context=ctx;
        DBHelper = new DatabaseHelper(context);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String BASE_DATOS = "reservacionlab.s3db"; //NOMBRE DE LA BASE
        private static final int VERSION = 1;
        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }


    @Override
    public void onCreate (SQLiteDatabase db){
        try{
            db.execSQL("CREATE TABLE asignatura(codigoAsignatura VARCHAR(10) NOT NULL PRIMARY KEY, nombreAsignatura VARCHAR(30) NOT NULL);");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        try { //Message.message(context,"OnUpgrade");
            db.execSQL(DROP_TABLE1);

            onCreate(db);
        }catch (Exception e) {
            //Message.message(context,""+e);
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
             if(verificarIntegridad(asignatura,2))
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



           //ACTUALIZAR ASIGNATURA

        public String actualizar(Asignatura asignatura){
             if(verificarIntegridad(asignatura,2)){
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

        // Eliminar Asignatura
        public String eliminar(Asignatura asignatura){
            String regAfectados="Filas afectadas=";
            int contador=0;

            if(verificarIntegridad(asignatura,2)){
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

    //CONSULTAR ASIGNATURA
        public Asignatura consultarAsignatura(String codigoAsignatura){

            String[] id={codigoAsignatura};
            Cursor cursor = db.query("asignatura", camposAsignatura, "codigoAsignatura=?", id, null,)
        }
    }






}
