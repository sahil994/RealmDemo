package com.hocrox.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    RealmAsyncTask realmAsyncTask;
    User user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                realm.delete(User.class);
                ContactInfo contactInfo = realm.createObject(ContactInfo.class);
                contactInfo.setEmail("goyal.sahil929@gmail.com");
                contactInfo.setMobileNumber("99154107902");


                RealmList<ContactInfo> realmList = new RealmList<ContactInfo>();
                realmList.add(contactInfo);
                realmList.add(contactInfo);

                User user = realm.createObject(User.class, 1);
                user.setAge(10);
                user.setName("SahilGoyal");
                user.setSessionId(1);
                user.setContactInfo(contactInfo);
                user.setContactInfoRealmList(realmList);


                User user1 = realm.createObject(User.class, 2);
                user1.setAge(111);
                user1.setName("SahilGoyal1");
                user1.setSessionId(1);
                user1.setContactInfo(contactInfo);
                //  user.setContactInfoRealmList(realmList);

            }

        });


      Log.e("check Auto Refresh",""+realm.isAutoRefresh());

        realm.where(User.class).equalTo("age", 10).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                User user = realm.where(User.class).equalTo("age", 10).findFirst();

                user.setAge(200);

            }
        });

        realmAsyncTask = realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                user2 = realm.createObject(User.class, 3);
                user2.setAge(113431);
                user2.setName("SahilGoyal1343");
                user2.setSessionId(5);
                realm.copyToRealmOrUpdate(user2);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Log.e("Testing", "success called");


            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("Testing", "errrrrr called>>>>>" + error.getMessage());


            }
        });


        RealmResults all = realm.where(User.class).findAll();


        Log.e("tesing", "" + all.size());

        for (int i = 0; i < all.size(); i++) {

            Log.e("testing", "" + all.get(i));
            User user = (User) all.get(i);
            Log.e("testing", "" + user.getName() + ">>" + user.getAge());

        }
        RealmResults<User> realmResults = realm.where(User.class).equalTo("contactInfo.Email", "goyal.sahil929@gmail.com").findAll();

        Log.e("searchreults", "" + realmResults.size());


        RealmResults<User> realmResults1 = realm.where(User.class).between("age", 10, 90).or().beginsWith("name", "SahilGoyal").findAll();

        RealmResults<User> realmResults2 = realmResults1.distinct("name");
        Log.e("Testoing", "" + realmResults1);
        Log.e("Testoing", "" + realmResults2);

        realmResults1.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
            @Override
            public void onChange(RealmResults realmResults) {

                Log.e("Tesitngg changed list", "" + realmResults);

            }
        });


        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        final DynamicRealm dynamicRealm = DynamicRealm.getInstance(realmConfig);

        dynamicRealm.executeTransaction(new DynamicRealm.Transaction() {
            @Override
            public void execute(DynamicRealm realm) {

                DynamicRealmObject dynamicRealmObject = realm.createObject("User",11);
                dynamicRealmObject.set("name","sahilgoyalrealm");
                dynamicRealmObject.set("age",15);

            }
        });

         realmResults1.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<User>>() {
             @Override
             public void onChange(RealmResults<User> users, OrderedCollectionChangeSet changeSet) {

                 OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();


                 // Log.e("Tesitngg changed list", +">>>>>>>>>>>>>>>>>>"+modifications);
                // OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                 Log.e("Tesitngg changed list",">>>>");

                 for (OrderedCollectionChangeSet.Range range : modifications){

                 Log.e("Tesitngg changed list",">>>>."+range);

                 }
             }
         });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (realmAsyncTask != null && !realmAsyncTask.isCancelled()) {
            realmAsyncTask.isCancelled();
        }

    }


}
