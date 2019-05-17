package ro.alexsalupa97.bloodbank.Notificari;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TestJobService extends JobService {

    private Context context;

    public TestJobService(Context context) {
        this.context = context;
    }

    public TestJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("jobservice", "Job started in background");
                for (int i = 0; i < 10; i++) {
                    Log.d("jobservice", "run: " + i);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("jobservice", "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("jobservice", "Job stopped");
        return false;
    }
}
