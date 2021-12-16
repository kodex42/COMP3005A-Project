package comp.A.project.forms;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

public class FinancesFilterForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;

    public FinancesFilterForm() {
        this.start = Date.from(Instant.now().minusSeconds(86400));
        this.end = Date.from(Instant.now().plusSeconds(86400));
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = DateUtils.setHours(DateUtils.setMinutes(start, 0),0);
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = DateUtils.setHours(DateUtils.setMinutes(end, 59),23);
    }
}
