package com.example.mpmazagi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    Button btnRegister;
    EditText emailEt, passEt, confirmEt;
    AppDatabase appDatabase = new AppDatabase(this);
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        emailEt =(EditText) findViewById(R.id.emailEt);
        passEt = (EditText) findViewById(R.id.passEt);
        confirmEt = (EditText) findViewById(R.id.confirmEt);
        btnRegister = findViewById(R.id.btnRegister);



       // user = new User(emailEt.getText().toString(), passEt.getText().toString());


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passEt.getText().length()==0 || confirmEt.getText().length()==0){

                    Toast.makeText(RegistrationActivity.this, "You Must Fill All The Fields", Toast.LENGTH_LONG).show();

                }else
                {
                    if ((passEt.getText().toString()).equals(confirmEt.getText().toString())){

                        appDatabase.addUserToDataBase(emailEt.getText().toString(),passEt.getText().toString());
                        inserApptSongsToDataBase();
                        Toast.makeText(RegistrationActivity.this, "Welcome To Mazagi \n Enjoy Our Music", Toast.LENGTH_LONG).show();
                        Intent goToLoginActivity = new Intent(RegistrationActivity.this, Login.class);
                        startActivity(goToLoginActivity);

                    } else {
                        Toast.makeText(RegistrationActivity.this, "Password And Confirm Password Doesn't Match!", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }

    public void inserApptSongsToDataBase(){

        appDatabase.addSongToDataBase("Moonlight Sonata","https://i.natgeofe.com/n/42e08d5a-5fbd-4c02-aa50-7e8a237aea72/16-beethoven-portrait-og_3x4.jpg","https://www.mfiles.co.uk/mp3-downloads/moonlight-movement1.mp3","Beethoven");
        appDatabase.addSongToDataBase("Pachelbel Canon","https://www.storytel.com/images/e/640x640/0002163680.jpg","https://www.mfiles.co.uk/mp3-downloads/pachelbels-canon-arranged.mp3","Beethoven");
        appDatabase.addSongToDataBase("Beethoven 5th","https://i.pinimg.com/236x/be/0e/df/be0edf2b0af211e242bb7b33055c5093.jpg","https://www.mfiles.co.uk/mp3-downloads/Beethoven-Symphony5-1.mp3","Beethoven");
        appDatabase.addSongToDataBase("Beethoven - Fur Elise","https://i.pinimg.com/236x/8d/a5/cc/8da5ccaf36799a142185a7e1ad2dd0bb.jpg","https://www.mfiles.co.uk/mp3-downloads/fur-elise.mp3","Beethoven");
        appDatabase.addSongToDataBase("The Sugar Plum Fairy","https://i.pinimg.com/564x/78/80/b8/7880b88cb62277e26a083bc8f4c0ae65.jpg","https://www.mfiles.co.uk/mp3-downloads/sugar-plum-fairy.mp3","Beethoven");
        appDatabase.addSongToDataBase("Toccata and Fugue in Dm","https://i.pinimg.com/236x/9a/7b/1f/9a7b1f834eb487e49b1c4a3bf886bc22.jpg","https://www.mfiles.co.uk/mp3-downloads/Toccata-and-Fugue-Dm.mp3","Beethoven");
        appDatabase.addSongToDataBase("Beethoven 7th, 2nd movement - Liszt Piano","https://i.pinimg.com/564x/a0/68/b9/a068b916ddbcb295abe7c5d7e577a890.jpg","https://www.mfiles.co.uk/mp3-downloads/beethoven-symphony7-2-liszt-piano.mp3","Beethoven");
        appDatabase.addSongToDataBase("Symphony No.6 1st movement ","https://i.pinimg.com/736x/cd/40/42/cd40420fd98bf2c165e1bd6635df94cb.jpg","https://www.mfiles.co.uk/mp3-downloads/beethoven-symphony6-1.mp3","Beethoven");
        appDatabase.addSongToDataBase("Haydn Piano Sonata No.31","https://i.pinimg.com/236x/25/0c/9a/250c9a2931f69a8ac8823138c4f7580a.jpg","https://www.mfiles.co.uk/mp3-downloads/haydn-piano-sonata-31-1.mp3","Beethoven");
        appDatabase.addSongToDataBase("Rondo alla Turca","https://i.pinimg.com/236x/9f/58/f0/9f58f04b54cfcacb77979c35f6cfc1a1.jpg","https://www.mfiles.co.uk/mp3-downloads/alla-turca.mp3","Mozart");
        appDatabase.addSongToDataBase("Eine Kleine Nachtmusik","https://i.pinimg.com/236x/4c/ab/65/4cab65a2148391236db7e25a244d1623.jpg","https://www.mfiles.co.uk/mp3-downloads/Eine-Kleine-Nachtmusik1-Violin.mp3","Mozart");
        appDatabase.addSongToDataBase("Abide with Me","https://i.pinimg.com/236x/92/0c/e4/920ce43838c3cc1a101157ba7cbc6f2a.jpg","https://www.mfiles.co.uk/mp3-downloads/abide-with-me.mp3","Mozart");
        appDatabase.addSongToDataBase("handel dead march from saul","https://i.pinimg.com/564x/e1/60/88/e160888c6111f6b599d8ae13152e533b.jpg","mfiles.co.uk/mp3-downloads/handel-dead-march-from-saul.mp3","Mozart");
        appDatabase.addSongToDataBase("symphony piano","https://i.pinimg.com/564x/94/06/5f/94065ff3297bde70cbe4ab6d98c72a35.jpg","https://www.mfiles.co.uk/mp3-downloads/mozart-symphony41-3-piano.mp3","Mozart");
        appDatabase.addSongToDataBase("symphony41","https://i.pinimg.com/564x/4c/97/ed/4c97ed99be0a3a87b71b959ad9d2900a.jpg","https://www.mfiles.co.uk/mp3-downloads/mozart-symphony41-3.mp3","Mozart");
        appDatabase.addSongToDataBase("Beethoven Minuet in G","https://i.pinimg.com/564x/cc/64/4f/cc644fef70a2e125ee6cf62fb4413e2c.jpg","https://www.mfiles.co.uk/mp3-downloads/beethoven-minuet-in-G-flute-piano.mp3","Beethoven");
        appDatabase.addSongToDataBase("Albeniz Tango Op.165 No.2","https://i.pinimg.com/564x/e1/60/88/e160888c6111f6b599d8ae13152e533b.jpg","https://www.mfiles.co.uk/mp3-downloads/isaac-albeniz-espana-tango-op165-no2-arranged-violin-and-piano.mp3","Beethoven");
        appDatabase.addSongToDataBase("Beethoven Ode to Joy","https://i.pinimg.com/236x/65/35/5b/65355b79a3d6647e7abfa13682c16790.jpg","https://www.mfiles.co.uk/mp3-downloads/beethoven-symphony9-4-ode-to-joy-bassoon-piano.mp3","Beethoven");
        appDatabase.addSongToDataBase("Debussy Arabesque no.1","https://i.pinimg.com/236x/60/f6/c1/60f6c1891b6c766c6a90aab9a4b7d960.jpg","https://www.mfiles.co.uk/mp3-downloads/arabesque1.mp3","Mozart");
        appDatabase.addSongToDataBase("Wagner Bridal Chorus","https://i.pinimg.com/236x/4e/34/c3/4e34c3eec1d34cc1d44d5b3ba3786b60.jpg","https://www.mfiles.co.uk/mp3-downloads/wagner-bridal-chorus.mp3","Mozart");
        appDatabase.addSongToDataBase("Oh Perfect Love for piano","https://i.pinimg.com/236x/a4/a5/a2/a4a5a25101a3452ca9c83020b4c99378.jpg","https://www.mfiles.co.uk/mp3-downloads/oh-perfect-love.mp3","Mozart");
        appDatabase.addSongToDataBase("Soldiers-March","https://i.pinimg.com/236x/d7/be/f5/d7bef5da76156cb451b92772cef3a033.jpg","https://www.mfiles.co.uk/mp3-downloads/Soldiers-March.mp3","Schumann");
        appDatabase.addSongToDataBase("Tchaikovsky-Chanson-Triste","https://i.pinimg.com/236x/d6/26/66/d626666bbfaa5428c1a085c5a13595cb.jpg","https://www.mfiles.co.uk/mp3-downloads/Tchaikovsky-Chanson-Triste-op40-no2.mp3","Schumann");
        appDatabase.addSongToDataBase("Song-of-the-Reapers","https://i.pinimg.com/236x/d2/cd/99/d2cd9975bcec067b4347cc7379a7f13a.jpg","https://www.mfiles.co.uk/mp3-downloads/Song-of-the-Reapers.mp3","Schumann");
        appDatabase.addSongToDataBase("mendelssohn-wedding-march","https://i.pinimg.com/236x/bf/90/3c/bf903cef4bf715bdadb5f311ac162d10.jpg","https://www.mfiles.co.uk/mp3-downloads/mendelssohn-wedding-march.mp3","Schumann");
        appDatabase.addSongToDataBase("wagner-bridal-chorus","https://i.pinimg.com/236x/75/04/a3/7504a316d07557f219ed8188fe85b16e.jpg","https://www.mfiles.co.uk/mp3-downloads/wagner-bridal-chorus.mp3","Schumann");
        appDatabase.addSongToDataBase("pachelbels-canon-arranged","https://i.pinimg.com/236x/c8/c8/05/c8c805ff743cd8ae1fbb0e8ed00bc6a9.jpg","https://www.mfiles.co.uk/mp3-downloads/pachelbels-canon-arranged.mp3","Schumann");
        appDatabase.addSongToDataBase("book1-prelude01","https://i.pinimg.com/236x/9c/46/fc/9c46fc126108fada4bccaa875dc1e045.jpg","https://www.mfiles.co.uk/mp3-downloads/book1-prelude01.mp3","Schumann");
        appDatabase.addSongToDataBase("oh-perfect-love","https://i.pinimg.com/236x/f9/90/e7/f990e72ab597638fe8e1da2800df28ca.jpg","https://www.mfiles.co.uk/mp3-downloads/oh-perfect-love.mp3","Schumann");
        appDatabase.addSongToDataBase("Oh Perfect Love piano","https://i.pinimg.com/564x/e9/07/f5/e907f58ac70fc7a5c1c32b8fee826265.jpg","https://www.mfiles.co.uk/mp3-downloads/oh-perfect-love.mp3","Schumann");


    }

}