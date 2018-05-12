package com.example.oscar.reservalab;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBDReservacionLab{

    private static final String[] camposCiclo=new String[] {"numCiclo", "anio"};
    private static final String[] camposAsignatura=new String[] {"codigoAsignatura","nombreAsignatura", "numCiclo"};




    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    private static final String DROP_TABLE1 ="DROP TABLE IF EXISTS asignatura; ";
    private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS ciclo; ";


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
        public void onCreate (SQLiteDatabase db){
            try{
                db.execSQL("CREATE TABLE asignatura(codigoAsignatura VARCHAR(10) NOT NULL PRIMARY KEY, nombreAsignatura VARCHAR(30) NOT NULL, numCiclo VARCHAR(2) NOT NULL);");
                db.execSQL("CREATE TABLE ciclo(numCiclo VARCHAR(2) NOT NULL PRIMARY KEY, anio VARCHAR(4) NOT NULL);");
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        @Override
        //  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub


//        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                //Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE1);
                db.execSQL(DROP_TABLE2);

                onCreate(db);
            }catch (Exception e) {
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

        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
         if(verificarIntegridad(asignatura,1)) {  // 1 Verificar integridad referencial
             if(verificarIntegridad(asignatura,3))// 2 Verificar registro duplicado
             { regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción"; }
             else { ContentValues asignaturas = new ContentValues();
                 asignaturas.put("codigoAsignatura", asignatura.getCodigoAsignatura());
                 asignaturas.put("nombreAsignatura", asignatura.getNombreAsignatura());
                 asignaturas.put("numCiclo", asignatura.getNumCiclo());

             contador=db.insert("asignatura", null, asignaturas); } }
             else { regInsertados= "Error al Insertar el registro, Registro sin referencias. Verificar inserción"; }
             regInsertados=regInsertados+contador;
         return regInsertados;

    }

     //INSERTAR CICLO
     public String insertar(Ciclo ciclo){

         String regInsertados="Registro Insertado Nº=";
         long contador=0;
         //Verificar que no exista ciclo
         if(verificarIntegridad(ciclo,2))
         {
             regInsertados="Error al Insertar el registros, Registro Duplicado. Verificar inserción";

         }
         else
         {
             ContentValues cicl = new ContentValues();
             cicl.put("numCiclo", ciclo.getNumCiclo());
             cicl.put("anio", ciclo.getAnio());
             contador=db.insert("ciclo", null, cicl);
             regInsertados=regInsertados+contador;
         }
         return  regInsertados;
     }

    //ACTUALIZAR ASIGNATURA

    public String actualizar(Asignatura asignatura){
        if(verificarIntegridad(asignatura,3)){
            String[] id={asignatura.getCodigoAsignatura()};
            ContentValues cv = new ContentValues();
            cv.put("codigoAsignatura", asignatura.getCodigoAsignatura());
            cv.put("nombreAsignatura", asignatura.getNombreAsignatura());
            cv.put("numCiclo", asignatura.getNumCiclo());
            db.update("asignatura", cv, "codigoAsignatura=?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con código de Asignatura"+ asignatura.getCodigoAsignatura()+ "No existe";
        }
    }

    //ACTUALIZAR CICLO
    public String actualizar(Ciclo ciclo){
        if(verificarIntegridad(ciclo,2)){
            String[] id={ciclo.getNumCiclo()};
            ContentValues cv = new ContentValues();
            cv.put("numCiclo", ciclo.getNumCiclo());
            cv.put("anio", ciclo.getAnio());

            db.update("ciclo", cv, "numCiclo=?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con código de Asignatura"+ ciclo.getNumCiclo()+ "No existe";
        }
    }


    // Eliminar Asignatura
    public String eliminar(Asignatura asignatura){

        String regAfectados="filas afectadas= "; int contador=0;
        // 2 Verificar registro que exista
        if(verificarIntegridad(asignatura, 3)) { String where="codigoAsignatura='"+asignatura.getCodigoAsignatura()+"'";
        where=where+" AND numCiclo='"+asignatura.getNumCiclo()+"'";
        contador+=db.delete("asignatura", where, null);
        regAfectados+=contador;
        return regAfectados;
        } else { return "Registro no Existe"; }
    }


    // Eliminar Ciclo
    public String ciclo(Ciclo ciclo){
        String regAfectados="Filas afectadas=";
        int contador=0;
         //Si existe ciclo
        if(verificarIntegridad(ciclo,2)){
            //Si tiene materias relacionadas, se eliminan primero
            if (verificarIntegridad(ciclo,4)){
                contador+=db.delete("ciclo","numCiclo='"+ciclo.getNumCiclo()+"'",null);
            }
            contador+=db.delete("asignatura","codigoAsignatura='"+ciclo.getNumCiclo()+"'", null);
            regAfectados+=contador;
        }

        else{
            return "Registro con código de Asignatura" +ciclo.getNumCiclo()+ "no existe";

        }

        return regAfectados;
    }

    //CONSULTAR ASIGNATURA
    public Asignatura consultarAsignatura(String codigoAsignatura, String nombreAsignatura, String numCiclo){

        String[] id={codigoAsignatura, nombreAsignatura, numCiclo};
        Cursor cursor = db.query("asignatura",camposAsignatura,"numCiclo =? AND codigoAsignatura=?", id, null, null, null);

        if(cursor.moveToFirst()){
            Asignatura asignatura = new Asignatura();
            asignatura.setCodigoAsignatura(cursor.getString(0));
            asignatura.setNombreAsignatura(cursor.getString(1));
            asignatura.setNumCiclo(cursor.getString(2));
            return asignatura;
        } else{
            return null;
        }

    }

    //CONSULTAR CICLO
    public Ciclo consultarCiclo(String numCiclo){

        String[] id={numCiclo};
        Cursor cursor = db.query("ciclo",camposCiclo,"numCiclo=?",id,null,null,null);

        if(cursor.moveToFirst()){
            Ciclo ciclo = new Ciclo();
            ciclo.setNumCiclo(cursor.getString(0));
            ciclo.setAnio(cursor.getString(1));
            return ciclo;
        } else{
            return null;
        }

    }

    private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1: { // Verifica que al insertar Asignatura exista ciclo
                Asignatura asignatura = (Asignatura)dato;
                String[] id1 = {asignatura.getNumCiclo()}; //Verifica que exista ciclo

                abrir();
                Cursor cursor1 = db.query("ciclo", null, "numCiclo = ?", id1, null, null, null);

                if(cursor1.moveToFirst()){ //Se encontraron datos ||
                    return true;
                } return false;
            }
            case 2:{
                //Verificar que exista ciclo
                Ciclo ciclo2 = (Ciclo)dato; String[] id = {ciclo2.getNumCiclo()};
                abrir();
                Cursor c2 = db.query("ciclo", null, "numCiclo = ?", id, null, null, null);
                if(c2.moveToFirst()){ //Se encontro Ciclo
                    return true; }
            }

            case 3:{ //Verificar que exista asignatura
                Asignatura asignatura2 = (Asignatura)dato; String[] id = {asignatura2.getCodigoAsignatura()};
                abrir();
                Cursor c2 = db.query("asignatura", null, "codigoAsignatura = ?", id, null, null, null);
                if(c2.moveToFirst()){ //Se encontro Asignatura
                    return true; }
            }

            case 4: { //Elimina las asignaturas
                Ciclo ciclo = (Ciclo)dato;
                Cursor c=db.query(true, "asignatura", new String[] { "carnet" }, "numCiclo='"+ciclo.getNumCiclo()+"'",null, null, null, null, null);
                if(c.moveToFirst())
                    return true;
                else return false;
            }


            default:
                return false; }

    }

    public  String llenarBDReservacionLab(){

        //variables para asignatura VA
        final String[] VAcodigo={"PRN115", "HDP115", "COS115", "MEP115","ANS115"};
        final String[] VAnombre={"Progamacion 1", "Herramientas de Producctividad", "Comunicaciones", "Metodos probabilisticos", "Analisis Numerico"};

        abrir();
        db.execSQL("DELETE FROM asignatura;");


        Asignatura asignatura = new Asignatura();
        for(int i=0; i<1; i++){
            asignatura.setCodigoAsignatura(VAcodigo[i]);
            asignatura.setNombreAsignatura(VAnombre[i]);
            insertar(asignatura);
        }

        cerrar();
        return "Guardado correctamente";
    }
}








