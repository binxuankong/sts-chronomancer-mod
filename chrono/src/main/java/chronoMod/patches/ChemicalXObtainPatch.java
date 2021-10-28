package chronoMod.patches;

import chronoMod.characters.Chronomancer;
import chronoMod.relics.Refresher;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ChemicalXObtainPatch {

    @SpirePatch(cls = "com.megacrit.cardcrawl.relics.ChemicalX", method = "makeCopy")
    public static class ChemicalXObtain {
        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic> Prefix(AbstractRelic _inst) {
            if (AbstractDungeon.player instanceof Chronomancer) {
                return SpireReturn.Return(new Refresher());
            }
            return SpireReturn.Continue();
        }
    }
}
