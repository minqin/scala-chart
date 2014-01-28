package scalax.chart

import java.util.Date
import scala.collection.JavaConversions.seqAsJavaList

import org.jfree.data.category._
import org.jfree.data.general._
import org.jfree.data.statistics._
import org.jfree.data.time._
import org.jfree.data.xy._

/** $RichChartingCollectionsInfo */
object RichChartingCollections extends RichChartingCollections

/** $RichChartingCollectionsInfo
  *
  * @define RichChartingCollectionsInfo Contains enrichments for collections for conversions to
  * datasets. To see which conversions are provided have a look at the classes defined below.
  *
  * @define name the name of the dataset
  *
  * @define autoSort whether or not the items in the series are sorted
  *
  * @define allowDuplicateXValues whether or not duplicate x-values are allowed
  */
trait RichChartingCollections {

  /** Enriches a collection of data pairs. */
  implicit class RichTuple2s[A,B](it: Iterable[(A,B)]) {

    /** Converts this collection to a `BoxAndWhiskerCategoryDataset`.
      *
      * @usecase def toBoxAndWhiskerCategoryDataset: BoxAndWhiskerCategoryDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerCategoryDataset[C](implicit eva: A ⇒ Comparable[A], evc: B ⇒ Seq[C], evd: C ⇒ Number): BoxAndWhiskerCategoryDataset = {
      val dataset = new DefaultBoxAndWhiskerCategoryDataset()
      it foreach { case (category,values) ⇒
        dataset.add(BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(seqAsJavaList(values)), category, "")
      }
      dataset
    }

    /** Converts this collection to a `BoxAndWhiskerXYDataset`.
      *
      * @usecase def toBoxAndWhiskerXYDataset(): BoxAndWhiskerXYDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerXYDataset[C](name: String = "")(implicit eva: A ⇒ Date, evb: B ⇒ Seq[C], evc: C ⇒ Number): BoxAndWhiskerXYDataset = {
      val dataset = new DefaultBoxAndWhiskerXYDataset(name)
      it foreach { case (date,ys) ⇒
        dataset.add(date, BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(seqAsJavaList(ys)))
      }
      dataset
    }

    /** Converts this collection to a `CategoryDataset`.
      *
      * @usecase def toCategoryDataset: CategoryDataset
      *   @inheritdoc
      */
    def toCategoryDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Number): CategoryDataset = {
      val dataset = new DefaultCategoryDataset
      it foreach { case (category,value) ⇒ dataset.addValue(value, category, "") }
      dataset
    }

    /** Converts this collection to a `PieDataset`.
      *
      * @usecase def toPieDataset: PieDataset
      *   @inheritdoc
      */
    def toPieDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Number): PieDataset = {
      val dataset = new DefaultPieDataset
      it foreach { case (category,value) ⇒ dataset.setValue(category, value) }
      dataset
    }

    /** Converts this collection to `TimePeriodValues`.
      *
      * @param name $name
      *
      * @usecase def toTimePeriodValues(): TimePeriodValues
      *   @inheritdoc
      */
    def toTimePeriodValues(name: String = "")(implicit eva: A ⇒ TimePeriod, evb: B ⇒ Number): TimePeriodValues = {
      val series = new TimePeriodValues(name)
      it foreach { case (t,v) ⇒ series.add(t,v) }
      series
    }

    /** Converts this collection to a `TimePeriodValuesCollection`.
      *
      * @param name $name
      *
      * @usecase def toTimePeriodValuesCollection(): TimePeriodValuesCollection
      *   @inheritdoc
      */
    def toTimePeriodValuesCollection(name: String = "")(implicit eva: A ⇒ TimePeriod, evb: B ⇒ Number): TimePeriodValuesCollection =
      new TimePeriodValuesCollection(toTimePeriodValues(name))

    /** Converts this collection to a `TimeSeries`.
      *
      * @param name $name
      *
      * @usecase def toTimeSeries(): TimeSeries
      *   @inheritdoc
      */
    def toTimeSeries(name: Comparable[_] = "")(implicit eva: A ⇒ RegularTimePeriod, evb: B ⇒ Number): TimeSeries = {
      val series = new TimeSeries(name)
      it foreach { case (time,value) ⇒ series.add(time,value) }
      series
    }

    /** Converts this collection to a `TimeSeriesCollection`.
      *
      * @param name $name
      *
      * @usecase def toTimeSeriesCollection(): TimeSeriesCollection
      *   @inheritdoc
      */
    def toTimeSeriesCollection(name: Comparable[_] = "")(implicit eva: A ⇒ RegularTimePeriod, evb: B ⇒ Number): TimeSeriesCollection =
      new TimeSeriesCollection(toTimeSeries(name))

    /** Converts this collection to an `XYSeries`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeries(): XYSeries
      *   @inheritdoc
      */
    def toXYSeries(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Number, evb: B ⇒ Number): XYSeries = {

      val series = new XYSeries(name, autoSort, allowDuplicateXValues)
      it foreach { case (x,y) ⇒ series.add(x,y) }
      series
    }

    /** Converts this collection to an `XYSeriesCollection`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeriesCollection(): XYSeriesCollection
      *   @inheritdoc
      */
    def toXYSeriesCollection(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Number, evb: B ⇒ Number): XYSeriesCollection =
      new XYSeriesCollection(toXYSeries(name, autoSort, allowDuplicateXValues))

  }

  /** Enriches a collection of data triples. */
  implicit class RichTuple3s[A,B,C](it: Iterable[(A,B,C)]) {

    /** Converts this collection to a `CategoryDataset`.
      *
      * @usecase def toCategoryDataset: CategoryDataset
      *   @inheritdoc
      */
    def toCategoryDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Comparable[B], evc: C ⇒ Number): CategoryDataset = {
      val dataset = new DefaultCategoryDataset
      it foreach { case (category,series,value) ⇒ dataset.addValue(value, series, category) }
      dataset
    }

  }

  /** Enriches a collection of data 4-tuples. */
  implicit class RichTuple4s[A,B,C,D](it: Iterable[(A,B,C,D)]) {

    /** Converts this collection to a `YIntervalSeries`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeries(): YIntervalSeries
      *   @inheritdoc
      */
    def toYIntervalSeries(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Double, evb: B ⇒ Double, evc: C ⇒ Double, evd: D ⇒ Double): YIntervalSeries = {

      val series = new YIntervalSeries(name, autoSort, allowDuplicateXValues)
      it foreach { case (x,y,yLow,yHigh) ⇒ series.add(x,y,yLow,yHigh) }
      series
    }

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Double, evb: B ⇒ Double, evc: C ⇒ Double, evd: D ⇒ Double): YIntervalSeriesCollection = {
      val series = toYIntervalSeries(name, autoSort, allowDuplicateXValues)
      val coll = new YIntervalSeriesCollection()
      coll.addSeries(series)
      coll
    }

  }

  /** Enriches a collection of mapped 3-tuples. */
  implicit class RichMappedTuple3s[A,B,C,D](it: Iterable[(A,(B,C,D))]) {

    /** Converts this collection to a `YIntervalSeries`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeries(): YIntervalSeries
      *   @inheritdoc
      */
    def toYIntervalSeries(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Double, evb: B ⇒ Double, evc: C ⇒ Double, evd: D ⇒ Double): YIntervalSeries = {

      val series = new YIntervalSeries(name, autoSort, allowDuplicateXValues)
      it foreach { case (x,(y,yLow,yHigh)) ⇒ series.add(x,y,yLow,yHigh) }
      series
    }

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param name $name
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(name: Comparable[_] = "", autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Double, evb: B ⇒ Double, evc: C ⇒ Double, evd: D ⇒ Double): YIntervalSeriesCollection = {
      val series = toYIntervalSeries(name, autoSort, allowDuplicateXValues)
      val coll = new YIntervalSeriesCollection()
      coll.addSeries(series)
      coll
    }

  }

  /** Enriches a collection of categorized data pairs. */
  implicit class RichCategorizedTuple2s[A,B,C](it: Iterable[(A,Iterable[(B,C)])]) {

    /** Converts this collection to a `BoxAndWhiskerCategoryDataset`.
      *
      * @usecase def toBoxAndWhiskerCategoryDataset: BoxAndWhiskerCategoryDataset
      *   @inheritdoc
      */
    def toBoxAndWhiskerCategoryDataset[D]
      (implicit eva: A ⇒ Comparable[A], evb: B ⇒ Comparable[B], evc: C ⇒ Seq[D], evd: D ⇒ Number): BoxAndWhiskerCategoryDataset = {

      val dataset = new DefaultBoxAndWhiskerCategoryDataset()
      for {
        (upper,lvs) ← it
        (lower,values) ← lvs
      } dataset.add(BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(seqAsJavaList(values)), lower, upper)
      dataset
    }

    /** Converts this collection to a `CategoryDataset`.
      *
      * @usecase def toCategoryDataset: CategoryDataset
      *   @inheritdoc
      */
    def toCategoryDataset(implicit eva: A ⇒ Comparable[A], evb: B ⇒ Comparable[B], evc: C ⇒ Number): CategoryDataset = {
      val dataset = new DefaultCategoryDataset
      for {
        (upper,lvs) ← it
        (lower,value) ← lvs
      } dataset.addValue(value, lower, upper)
      dataset
    }

    /** Converts this collection to a `CategoryTableXYDataset`.
      *
      * @usecase def toCategoryTableXYDataset: CategoryTableXYDataset
      *   @inheritdoc
      */
    def toCategoryTableXYDataset(implicit eva: A ⇒ String, evb: B ⇒ Number, evc: C ⇒ Number): CategoryTableXYDataset = {
      val dataset = new CategoryTableXYDataset
      for {
        (category,xys) ← it
        (x,y) ← xys
      } dataset.add(x, y, category, false)
      dataset
    }

    /** Converts this collection to a time table.
      *
      * @usecase def toTimeTable: TimeTableXYDataset
      *   @inheritdoc
      */
    def toTimeTable(implicit eva: A ⇒ Comparable[A], evb: B ⇒ TimePeriod, evc: C ⇒ Number): TimeTableXYDataset = {
      val dataset = new TimeTableXYDataset
      for {
        (category,tvs) ← it
        (time,value) ← tvs
      } dataset.add(time,value,category,false)
      dataset
    }

    /** Converts this collection to an `XYSeriesCollection`.
      *
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toXYSeriesCollection(): XYSeriesCollection
      *   @inheritdoc
      */
    def toXYSeriesCollection(autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Comparable[A], evb: B ⇒ Number, evc: C ⇒ Number): XYSeriesCollection = {

      val seriess = for {
        (category,xys) ← it
      } yield xys.toXYSeries(category, autoSort, allowDuplicateXValues)
      seriess.toXYSeriesCollection
    }

  }

  /** Enriches a collection of categorized 4-tuples. */
  implicit class RichCategorizedTuple4s[A,B,C,D,E](it: Iterable[(A,Iterable[(B,C,D,E)])]) {

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Comparable[_], evb: B ⇒ Double, evc: C ⇒ Double, evd: D ⇒ Double, eve: E ⇒ Double): YIntervalSeriesCollection = {

      val seriess = for ((name,data) ← it) yield
        data.toYIntervalSeries(name, autoSort, allowDuplicateXValues)

      seriess.toYIntervalSeriesCollection
    }

  }

  /** Enriches a collection of categorized mapped 3-tuples. */
  implicit class RichCategorizedMappedTuple3s[A,B,C,D,E](it: Iterable[(A,Iterable[(B,(C,D,E))])]) {

    /** Converts this collection to a `YIntervalSeriesCollection`.
      *
      * @param autoSort $autoSort
      * @param allowDuplicateXValues $allowDuplicateXValues
      *
      * @usecase def toYIntervalSeriesCollection(): YIntervalSeriesCollection
      *   @inheritdoc
      */
    def toYIntervalSeriesCollection(autoSort: Boolean = true, allowDuplicateXValues: Boolean = true)
      (implicit eva: A ⇒ Comparable[_], evb: B ⇒ Double, evc: C ⇒ Double, evd: D ⇒ Double, eve: E ⇒ Double): YIntervalSeriesCollection = {

      val seriess = for ((name,data) ← it) yield
        data.toYIntervalSeries(name, autoSort, allowDuplicateXValues)

      seriess.toYIntervalSeriesCollection
    }

  }

  /** Enriches a collection of categorized categorized data pairs. */
  implicit class RichCategorizedCategorizedTuple2s[A,B,C,D](it: Iterable[(A,Iterable[(B,Iterable[(C,D)])])]) {

    import org.jfree.chart.JFreeChart
    import org.jfree.chart.plot._

    /** Converts this collection to a bar chart with a `CombinedDomainCategoryPlot`.
      *
      * @usecase def toCombinedDomainBarChart: CategoryChart
      *   @inheritdoc
      */
    def toCombinedDomainBarChart(implicit eva: A ⇒ Comparable[A],
                                          evb: B ⇒ Comparable[B],
                                          evc: C ⇒ Comparable[C],
                                          evd: D ⇒ Number): CategoryChart = {
      val plot = new CombinedDomainCategoryPlot

      for {
        (outer,miv) ← it
        dataset     = miv.toCategoryDataset
        chart       = ChartFactories.BarChart(dataset, outer.toString)
      } plot.add(chart.plot)

      val chart = new JFreeChart(plot)

      new CategoryChart {
        override val peer = chart
      }
    }

  }

  // -----------------------------------------------------------------------------------------------
  // convert a collection of *Series to a *Collection / *Dataset
  // -----------------------------------------------------------------------------------------------

  /** Enriches a collection of `TimeSeries`. */
  implicit class RichTimeSeriesCollection(it: Iterable[TimeSeries]) {
    /** Converts this collection of `TimeSeries` to a `TimeSeriesCollection`. */
    def toTimeSeriesCollection: TimeSeriesCollection = {
      val coll = new TimeSeriesCollection
      it foreach coll.addSeries
      coll
    }
  }

  /** Enriches a collection of `XYSeries`. */
  implicit class RichXYSeriesCollection(it: Iterable[XYSeries]) {
    /** Converts this collection of `XYSeries` to a `XYSeriesCollection`. */
    def toXYSeriesCollection: XYSeriesCollection = {
      val coll = new XYSeriesCollection
      it foreach coll.addSeries
      coll
    }
  }

  /** Enriches a collection of `TimePeriodValues`. */
  implicit class RichTimePeriodValuesCollection(it: Iterable[TimePeriodValues]) {
    /** Converts this collection of `TimePeriodValues` to a `TimePeriodValuesCollection`. */
    def toTimePeriodValuesCollection: TimePeriodValuesCollection = {
      val coll = new TimePeriodValuesCollection
      it foreach coll.addSeries
      coll
    }
  }

  /** Enriches a collection of `YIntervalSeries`. */
  implicit class RichYIntervalSeriesCollection(it: Iterable[YIntervalSeries]) {
    /** Converts this collection of `YIntervalSeries` to a `YIntervalSeriesCollection`. */
    def toYIntervalSeriesCollection: YIntervalSeriesCollection = {
      val coll = new YIntervalSeriesCollection
      it foreach coll.addSeries
      coll
    }
  }

}
