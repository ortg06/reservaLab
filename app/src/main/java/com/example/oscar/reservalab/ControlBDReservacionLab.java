package com.example.oscar.reservalab;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBDReservacionLab{

    private static final String[] camposCiclo=new String[] {"idCiclo","numCiclo", "anio"};
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
                db.execSQL("CREATE TABLE asignatura(codigoAsignatura VARCHAR(10) NOT NULL PRIMARY KEY, nombreAsignatura VARCHAR(30) NOT NULL, idCiclo INTEGER NOT NULL);");
                db.execSQL("CREATE TABLE ciclo(idCiclo Integer NOT NULL PRIMARY KEY, numCiclo Integer NOT NULL, anio Integer NOT NULL);");
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
        ContentValues asig = new ContentValues();
        asig.put("codigoAsignatura", asignatura.getCodigoAsignatura());
        asig.put("nombreAsignatura", asignatura.getNombreAsignatura());
        asig.put("idCiclo", asignatura.getIdCiclo());

        contador=db.insert("asignatura", null, asig);
        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
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
             cicl.put("idCiclo", ciclo.getIdCiclo());
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
            cv.put("idCiclo", asignatura.getIdCiclo());
            db.update("asignatura", cv, "codigoAsignatura=?", id);
            return "Registro Actualizado Correctamente";
        }else{
            return "Registro con código de Asignatura"+ asignatura.getCodigoAsignatura()+ "No existe";
        }
    }

    //ACTUALIZAR CICLO
    public String actualizar(Ciclo ciclo){
        if(verificarIntegridad(ciclo,2)){
            String[] id={Integer.toString(ciclo.getIdCiclo())};
            ContentValues cv = new ContentValues();
            cv.put("idCiclo", ciclo.getIdCiclo());
            cv.put("numCiclo", ciclo.getNumCiclo());
            cv.put("anio", ciclo.getAnio());

            db.update("ciclo", cv, "idCiclo=?", id);
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
        where=where+" AND idCiclo='"+asignatura.getIdCiclo()+"'";
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
                contador+=db.delete("ciclo","idCiclo='"+ciclo.getIdCiclo()+"'",null);
            }
            contador+=db.delete("asignatura","codigoAsignatura='"+ciclo.getIdCiclo()+"'", null);
            regAfectados+=contador;
        }

        else{
            return "Registro con código de Asignatura" +ciclo.getIdCiclo()+ "no existe";

        }

        return regAfectados;
    }

    //CONSULTAR ASIGNATURA
    public Asignatura consultarAsignatura(String codigoAsignatura, String nombreAsignatura, Integer idCiclo){
        String idciclo;
        idciclo=Integer.toString(idCiclo);
        String[] id={codigoAsignatura, nombreAsignatura, idciclo};
        Cursor cursor = db.query("asignatura",camposAsignatura,"idCiclo =? AND codigoAsignatura=?", id, null, null, null);

        if(cursor.moveToFirst()){
            Asignatura asignatura = new Asignatura();
            asignatura.setCodigoAsignatura(cursor.getString(0));
            asignatura.setNombreAsignatura(cursor.getString(1));
            asignatura.setIdCiclo(cursor.getInt(2));

            return asignatura;
        } else{
            return null;
        }

    }

    //CONSULTAR CICLO
    public Ciclo consultarCiclo(Integer idCiclo){

        String idciclo;
        idciclo=Integer.toString(idCiclo);

        String[] id={idciclo};
        Cursor cursor = db.query("ciclo",camposCiclo,"idCiclo=?",id,null,null,null);

        if(cursor.moveToFirst()){
            Ciclo ciclo = new Ciclo();
            ciclo.setIdCiclo(cursor.getInt(0));
            ciclo.setNumCiclo(cursor.getInt(1));
            ciclo.setAnio(cursor.getInt(2));
            return ciclo;
        } else{
            return null;
        }

    }

    private boolean verificarIntegridad(Object dato, int relacion) throws SQLException{
        switch (relacion){
            case 1: { // Verifica que al insertar Asignatura exista ciclo
                Asignatura asignatura = (Asignatura)dato;
                String[] id1 = {Integer.toString(asignatura.getIdCiclo())}; //Verifica que exista ciclo

                abrir();
                Cursor cursor1 = db.query("ciclo", null, "idCiclo = ?", id1, null, null, null);

                if(cursor1.moveToFirst()){ //Se encontraron datos ||
                    return true;
                } return false;
            }
            case 2:{
                //Verificar que exista ciclo
                Ciclo ciclo2 = (Ciclo)dato; String[] id = {Integer.toString(ciclo2.getNumCiclo())};
                abrir();
                Cursor c2 = db.query("ciclo", null, "idCiclo = ?", id, null, null, null);
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
                Cursor c=db.query(true, "asignatura", new String[] { "codigoAsignatura" }, "idCiclo='"+ciclo.getIdCiclo()+"'",null, null, null, null, null);
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
        final Integer[] VAnumCiclo={01,02};

        //Variables para Ciclo VC
        final Integer[] VCidCiclo={1,2,3,4};
        final Integer[] VCnumCico={01,02};
        final Integer[] VCanio={2017, 2018};

        abrir();
        db.execSQL("DELETE FROM asignatura;");
        db.execSQL("DELETE FROM ciclo;");


        Asignatura asignatura = new Asignatura();
        for(int i=0; i<2; i++){
            asignatura.setCodigoAsignatura(VAcodigo[i]);
            asignatura.setNombreAsignatura(VAnombre[i]);
            asignatura.setIdCiclo(VAnumCiclo[i]);
            insertar(asignatura);
        }

        Ciclo ciclo = new Ciclo();
        for(int i=0; i<2; i++){
            ciclo.setIdCiclo(VCidCiclo[i]);
            ciclo.setNumCiclo(VCnumCico[i]);
            ciclo.setAnio(VCanio[i]);
            insertar(ciclo);
        }

        cerrar();
        return "Guardado correctamente";
    }
}








